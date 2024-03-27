
describe("Mainline Scenario Test", () => {

    it("Create Bank Account", async () => {
        await browser.url("http://localhost:4200/#/bank-accounts")
      
        const addBankAccountButton = await $('#add-bank-account');
        await addBankAccountButton.click();

        const nameInput = await $('#name-input');
        const descriptionInput = await $('#description-input');
        const balanceInput = await $('#balance-input');
        const dispoInput = await $('#dispo-input');

        await nameInput.addValue('Account')
        await descriptionInput.setValue('My Account')
        await balanceInput.setValue(100)
        await dispoInput.setValue(0);

        const createBankAccountButton = await $('#create-bank-account');
        await createBankAccountButton.click();

        const bankAccountTable = await $('table tbody')
        await expect(bankAccountTable).toHaveChildren(1)
    });

    it("Create Rule-Based Savings Goal", async () => {
        await browser.url("http://localhost:4200/#/savings-goals/rule-based")
      
        const addSavingsGoalButton = await $('#add-savings-goal');
        await addSavingsGoalButton.click();

        const nameInput = await $('#name-input');
        const descriptionInput = await $('#description-input');
        const matchingTypeInput = await $('#matching-type-selector');
        const matchingType = await $('#MATCH_ALL');

        await nameInput.setValue('Minimum Balance')
        await descriptionInput.setValue('No Bank Account under 50')
        await matchingTypeInput.click();
        await matchingType.click();

        const createSavingsGoalButton = await $('#create-savings-goal');
        await createSavingsGoalButton.click();

        const ruleBasedSavingsGoalTable = await $('table tbody');
        await expect(ruleBasedSavingsGoalTable).toHaveChildren(1);

        const expandSavingsGoalButton = await $('#expand-savings-goal');
        await expandSavingsGoalButton.click();

        const addRuleButton = await $('#add-rule');
        await addRuleButton.click();

        const bankAccountSelector = await $('#bank-account-selector');
        const bankAccount = await $('#Account');
        const targetAmountInput = await $('#target-amount-input');
        const typeSelector = await $('#type-selector');
        const type = await $('#GREATER_THAN');

        await bankAccountSelector.click();
        await bankAccount.click();
        await targetAmountInput.setValue(50);
        await typeSelector.click();
        await type.click();

        const createRuleButton = await $('#create-rule');
        await createRuleButton.click();

        const rulesTable = await $('table tbody');
        await expect(rulesTable).toHaveChildren(1);
    });

    it("Create Product", async () => {
        await browser.url("http://localhost:4200/#/products")
      
        const addProductButton = await $('#add-product');
        await addProductButton.click();

        const nameInput = await $('#name-input');
        const priceInput = await $('#price-input');
        const sizeInput = await $('#size-input');

        await nameInput.setValue('Mango')
        await priceInput.setValue(5)
        await sizeInput.setValue(500)

        const createProductButton = await $('#create-product');
        await createProductButton.click();

        const productTable = await $('table tbody')
        await expect(productTable).toHaveChildren(1)
    });

    it("Add Product to Shopping Cart", async () => {
        await browser.url("http://localhost:4200/#/shopping-cart")
      
        const addProductButton = await $('#add-product');
        await addProductButton.click();
 
        const productSelector = await $('#product-selector');
        const product = await $('#Mango');
        const quantityInput = await $('#quantity-input');

        await productSelector.click();
        await product.click();
        await quantityInput.setValue(10);

        const createProductEntryButton = await $('#add-product-to-cart');
        await createProductEntryButton.click();

        const shoppingCartTable = await $('table tbody');
        await expect(shoppingCartTable).toHaveChildren(1);
    });

    it("Purchase Shopping Cart", async () => {
        await browser.url("http://localhost:4200/#/shopping-cart")

        const purchaseCheckbox = await $('#purchase-checkbox');
        await purchaseCheckbox.click();

        const bookPurchasesButton = await $('#book-purchases');
        await bookPurchasesButton.click();

        const confirmPurchasesButton = await $('button*=Yes, book it!');
        await confirmPurchasesButton.click();

        const shoppingCartTable = await $('table tbody');
        await expect(shoppingCartTable).toHaveChildren(0);
    });

    it("Validate Transaction, BankAccount & SavingsGoal", async () => {
        await browser.url("http://localhost:4200/#/bank-accounts")

        const balance = await $('#balance');
        await expect(balance).toHaveText("50");

        await browser.url("http://localhost:4200/#/transactions/one-time")

        const transactionsTable = await $('table tbody');
        await expect(transactionsTable).toHaveChildren(1);

        await browser.url("http://localhost:4200/#/savings-goals/rule-based")

        const status = await $('#status');
        await expect(status).toHaveText("FAILED");
    });
    
  });
