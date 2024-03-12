sequenceDiagram

    #createbankaccount, savingsgoalandproduct

    USER->>+BankAccount: POST "/bankAccounts {name:savings_account}"

    BankAccount-->>-USER: created bank account "savings_account"

    USER->>+SavingsGoal: POST "/savings-goals/rule-based, {"nodepth", ANY_MATCH, savings_accountrules:[{type:GREATER_THAN, amount:0,00€},{type:EQUALS, amount:0,00€}]}"

    SavingsGoal->>+USER: new rule based savings goal created

    USER->>+Product: POST "/products, {name:mango, category:food, price:2€}"

    Product-->>-USER: created new product entry "mango"

    #createshoppingcartentryfortheproduct

    USER->>+Product: POST "/supplies/entries, {name:mango}"

    Product-->>-USER: item "mango" was placed in supplies

    USER->>+Product: POST "/supplies/shop, {name:mango}"

    Product-->>-USER: item "mango" was placed into the shopping cart

    #showtheshoppingcartscontentsandpurchasethem

    USER->>+Product: GET "/shopping-cart"

    Product-->>-USER: shoppingcarthasthefollowingitems: ["mango"]

    USER->>+Product: POST "/shopping-cart/purchase, {name:savings_account}"

    Product-->>-USER: the items in the shopping cart have been purchased by "savings_account"

    #checkthesavingsgoal

    USER->>+SavingsGoal: GET "/savings-goals/rule-based, {name:no depth}"

    SavingsGoal-->>-USER: rule "no depth" "FAILED"

    #createincome

    USER->>+Transaction: POST "/transactions/recurring, {bank_account:savings_account,money:1200€,frequency:MONTHLY}"

    Transaction-->>-USER: new "monthly" "income" for "savings_account" created

    #checkAnalytics

    USER->>+Analytics: GET "/report"

    Analytics-->>-USER: excel file has been created

    USER->>+Analytics: GET "/nutrition"

    Analytics-->>-USER: "nutrition Values"
