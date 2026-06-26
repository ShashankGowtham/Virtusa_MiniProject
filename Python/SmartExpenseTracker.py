expenses = []


def add_expense():

    name = input("Enter Name : ")
    category = input("Enter Category : ")
    amount = float(input("Enter Amount : "))

    row = {
        "name": name,
        "category": category,
        "amount": amount
    }

    expenses.append(row)

    print("Expense Added Successfully")


def view_expenses():

    if len(expenses) == 0:
        print("No Expenses Found")
        return

    print("\nExpense List")

    # Display every expense entered by the user
    for item in expenses:
        print(item["name"], "-", item["category"], "- ₹", item["amount"])


def category_summary():

    summary = {}

    # Calculate total expense for each category
    for item in expenses:

        key = item["category"]

        if key not in summary:
            summary[key] = 0

        summary[key] += item["amount"]

    print("\nCategory Summary")

    # Display category wise total amount
    for key in summary:
        print(key, ":", summary[key])


def highest_expense():

    if len(expenses) == 0:
        print("No Expenses Found")
        return

    top = expenses[0]

    # Find the highest expense
    for item in expenses:

        if item["amount"] > top["amount"]:
            top = item

    print("\nHighest Expense")
    print("Name :", top["name"])
    print("Category :", top["category"])
    print("Amount : ₹", top["amount"])


def total_expense():

    total = 0

    # Add all expense amounts
    for item in expenses:
        total += item["amount"]

    print("Total Expense : ₹", total)


while True:

    print("\n===== Smart Expense Tracker =====")
    print("1. Add Expense")
    print("2. View Expenses")
    print("3. Category Summary")
    print("4. Highest Expense")
    print("5. Total Expense")
    print("6. Exit")

    ch = input("Enter Choice : ")

    if ch == "1":
        add_expense()

    elif ch == "2":
        view_expenses()

    elif ch == "3":
        category_summary()

    elif ch == "4":
        highest_expense()

    elif ch == "5":
        total_expense()

    elif ch == "6":
        print("Program Ended")
        break

    else:
        print("Invalid Choice")