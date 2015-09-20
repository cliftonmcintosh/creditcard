package com.cliftonmcintosh.creditcard;


import com.cliftonmcintosh.creditcard.accounts.Account;
import com.cliftonmcintosh.creditcard.accounts.AccountService;
import com.cliftonmcintosh.creditcard.accounts.CreditCardAccountService;
import com.cliftonmcintosh.creditcard.errors.AccountErrorService;
import com.cliftonmcintosh.creditcard.errors.ErrorService;
import com.cliftonmcintosh.creditcard.input.InputProcessor;
import com.cliftonmcintosh.creditcard.input.SpaceDelimitedInputProcessor;
import com.cliftonmcintosh.creditcard.validation.AccountValidationService;
import com.cliftonmcintosh.creditcard.validation.CreditCardAccountValidationService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Consumer;

public class Application {

    private final AccountService accountService;
    private final InputProcessor inputProcessor;

    private final Map<String, Consumer<List<String>>> commandConsumerMap;


    public static void main(String[] args) {
        AccountValidationService validationService = new CreditCardAccountValidationService();
        ErrorService errorService = new AccountErrorService();
        AccountService anAccountService = new CreditCardAccountService(validationService, errorService);
        InputProcessor anInputProcessor = new SpaceDelimitedInputProcessor();
        Application application = new Application(anAccountService, anInputProcessor);
        application.run(args);
    }

    public Application(AccountService accountService, InputProcessor inputProcessor) {
        this.accountService = Preconditions.checkNotNull(accountService, "The account service cannot be null");
        this.inputProcessor = Preconditions.checkNotNull(inputProcessor, "The input processor cannot be null");
        commandConsumerMap = new HashMap<>();
        commandConsumerMap.put("Add", buildAddConsumerCommand());
    }

    public void run(String... args) {
        System.out.println("Welcome to the Credit Card Processing Application");
        boolean more = true;
        while (more) {
            System.out.print("Enter a command: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            List<String> tokenizedInput = Lists.newArrayList(inputProcessor.processRawInput(input));
            tokenizedInput.addAll(Lists.newArrayList("", "", "", ""));
            if (commandConsumerMap.containsKey(tokenizedInput.get(0))) {
                commandConsumerMap.get(tokenizedInput.get(0)).accept(tokenizedInput);
            }
            more = !"exit".equalsIgnoreCase(input);
        }

    }

    private Consumer<List<String>> buildAddConsumerCommand() {
        return (List<String> input) -> {
            if (!input.get(1).isEmpty()) {
                accountService.createAccount(input.get(1), input.get(2), input.get(3));
                Set<Account> accounts = accountService.getAccounts();
                accounts.forEach(account -> System.out.println(String.format("Account Name: %s, Number: %s, Limit: %d, Balance: %d",
                        account.getName(), account.getAccountNumber(), account.getLimit(), account.getBalance())));
            }
        };
    }
}
