import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BankY {
    private Map<String, Account> accounts;
    private final String dataFile = "bank_data.txt";

    public BankY() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    public void createAccount(String accountId, double initialBalance) {
        if (!accounts.containsKey(accountId)) {
            accounts.put(accountId, new Account(accountId, initialBalance));
            saveAccounts();
            System.out.println("Account created: " + accountId + " with balance " + initialBalance);
        } else {
            System.out.println("Account already exists.");
        }
    }

    public void deposit(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            account.deposit(amount);
            saveAccounts();
            System.out.println("Deposited " + amount + " to account " + accountId);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            if (account.withdraw(amount)) {
                saveAccounts();
                System.out.println("Withdrew " + amount + " from account " + accountId);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = accounts.get(fromAccountId);
        Account toAccount = accounts.get(toAccountId);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                saveAccounts();
                System.out.println("Transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId);
            } else {
                System.out.println("Insufficient balance in source account.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public void showBalance(String accountId) {
        Account account = accounts.get(accountId);
        if (account != null) {
            System.out.println("Account " + accountId + " balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    public void summarizeAccounts() {
        System.out.println("Summary of all accounts:");
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("Account " + entry.getKey() + ": " + entry.getValue().getBalance());
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            accounts = (Map<String, Account>)ois.readObject();
        } catch (FileNotFoundException e) {
            accounts = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BankY bank = new BankY();
        Scanner scanner = new Scanner(System.in);
        int command;

        System.out.println("Welcome to BankY!");

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Create account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Show balance");
            System.out.println("6. Summarize accounts");
            System.out.println("7. Exit");
            command = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (command == 7) {
                break;
            }

            String accountId;

            switch (command) {
                case 1:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    bank.createAccount(accountId, initialBalance);
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    bank.deposit(accountId, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    bank.withdraw(accountId, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter source account ID: ");
                    String fromAccountId = scanner.nextLine();
                    System.out.print("Enter destination account ID: ");
                    String toAccountId = scanner.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    bank.transfer(fromAccountId, toAccountId, transferAmount);
                    break;
                case 5:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.nextLine();
                    bank.showBalance(accountId);
                    break;
                case 6:
                    bank.summarizeAccounts();
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }
}

class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountId;
    private double balance;

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Invalid or insufficient balance for withdrawal.");
            return false;
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }
}
