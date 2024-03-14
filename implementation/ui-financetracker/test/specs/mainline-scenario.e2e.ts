
describe("Mainline Scenario Test", () => {

    it("Create Bank Account", async () => {
        await browser.url("http://localhost:4200/#/bank-accounts")
      
        const addBankAccountButton = await $('#add-bank-account');
        await addBankAccountButton.click();

        const nameInput = await $('#name-input');
        const descriptionInput = await $('#description-input');
        const balanceInput = await $('#balance-input');
        const dispoInput = await $('#dispo-input');

        await nameInput.setValue('Account')
        await descriptionInput.setValue('My Account')
        await balanceInput.setValue(100)
        await dispoInput.setValue(0);

        const createBankAccountButton = await $('#create-bank-account');
        await createBankAccountButton.click();

        const bankAccountTable = await $('table tbody')
        await expect(bankAccountTable).toHaveChildren(1)
    });
    
  });



