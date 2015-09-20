package com.cliftonmcintosh.creditcard;


import com.cliftonmcintosh.creditcard.accounts.AccountService;
import com.cliftonmcintosh.creditcard.accounts.CreditCardAccountService;
import com.cliftonmcintosh.creditcard.errors.AccountErrorService;
import com.cliftonmcintosh.creditcard.errors.ErrorService;
import com.cliftonmcintosh.creditcard.input.InputProcessor;
import com.cliftonmcintosh.creditcard.input.SpaceDelimitedInputProcessor;
import com.cliftonmcintosh.creditcard.reporting.Message;
import com.cliftonmcintosh.creditcard.reporting.Messages;
import com.cliftonmcintosh.creditcard.validation.AccountValidationService;
import com.cliftonmcintosh.creditcard.validation.CreditCardAccountValidationService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Application {

    private final AccountService accountService;
    private final InputProcessor inputProcessor;
    private final ErrorService errorService;

    private final Map<String, Consumer<List<String>>> commandConsumerMap;


    public static void main(String[] args) {
        AccountValidationService validationService = new CreditCardAccountValidationService();
        ErrorService errorService = new AccountErrorService();
        AccountService accountService = new CreditCardAccountService(validationService, errorService);
        InputProcessor inputProcessor = new SpaceDelimitedInputProcessor();
        Application application = new Application(accountService, inputProcessor, errorService);
        application.run(args);
    }

    public Application(AccountService accountService, InputProcessor inputProcessor, ErrorService errorService) {
        this.accountService = Preconditions.checkNotNull(accountService, "The account service cannot be null");
        this.inputProcessor = Preconditions.checkNotNull(inputProcessor, "The input processor cannot be null");
        this.errorService = Preconditions.checkNotNull(errorService, "The error service cannot be null");
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
            final List<Message> messages = buildSortedAccountAndErrorMessages();

            messages.forEach(message -> System.out.println(String.format("%s: %s", message.getHeader(), message.getDetails())));
            System.out.println("--------------------\n");

            more = !"exit".equalsIgnoreCase(input);
        }

    }

    private Consumer<List<String>> buildAddConsumerCommand() {
        return (List<String> input) -> {
            if (!input.get(1).isEmpty()) {
                accountService.createAccount(input.get(1), input.get(2), input.get(3));
            }
        };
    }

    private List<Message> buildSortedAccountAndErrorMessages() {
        final List<Message> allMessages = new ArrayList<>();

        final List<Message> accountMessages = accountService.getAccounts()
                .stream()
                .map(Messages.ACCOUNT_MESSAGE_FUNCTION)
                .collect(Collectors.toList());

        final List<Message> errorMessages = errorService.getErrors()
                .stream()
                .map(Messages.ERROR_MESSAGE_FUNCTION)
                .collect(Collectors.toList());

        allMessages.addAll(accountMessages);
        allMessages.addAll(errorMessages);

        return allMessages.stream().sorted(Messages.MESSAGE_HEADER_COMPARATOR).collect(Collectors.toList());
    }
}
