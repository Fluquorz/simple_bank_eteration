Collections:

MAPPING TYPE: GET
localhost:8080/account/v1/get //get all accounts
-----------------------------------------------------------------

MAPPING TYPE: GET
localhost:8080/account/v1/get/{accountNumber} //get account find by account number

example:
localhost:8080/account/v1/get/1212
-----------------------------------------------------------------

MAPPING TYPE: POST
localhost:8080/account/v1/create //create account with phonenumber and provider for actions these actions: deposit, withdraw and phone bill payment
{
    "accountNumber": "1212",
    "ownerName": "Ahmet Emin Kahraman",
    "balance": 5000.0,
    "phoneNumber": "5551234537",
    "provider": "VODAPHONE"
}

or //create account for default actions (deposit and withdraw) 

{
    "accountNumber": "1212",
    "ownerName": "Ahmet Emin Kahraman",
    "balance": 5000.0
}
-----------------------------------------------------------------

MAPPING TYPE: POST
localhost:8080/account/v1/post/{accountNumber} //post an action for your bank account, the actions are: PHONE_BILL, WITHDRAW and DEPOSIT

example:
localhost:8080/account/v1/post/1212
{
    "amount": 120.0,
    "type": "PHONE_BILL"
}

or

{
    "amount": 120.0,
    "type": "WITHDRAW"
}

or

{
    "amount": 120.0,
    "type": "DEPOSIT"
}
-----------------------------------------------------------------

MAPPING TYPE: DELETE
localhost:8080/account/v1/delete/{accountNumber}// for deleting account
localhost:8080/account/v1/delete/1212
