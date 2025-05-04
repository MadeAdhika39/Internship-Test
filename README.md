# Openway-Internship-Test
Automated testing for google mail (Scenario B)

Part 1: Components of a Test Case
A well-constructed test case typically includes the following components:

Test Case ID: A unique identifier for tracking the test case.

Test Case Title: A brief name that describes what the test is about.

Preconditions: The state or setup required before the test begins.

Test Steps: Clear, step-by-step instructions on how to execute the test.

Test Data: Specific data to be used in the test (e.g., email subject, user credentials).

Expected Result: What you expect to happen after performing the steps.

Status: Pass/Fail, based on whether the actual result matches the expected.

Comments: Optional notes, e.g., issues encountered or screenshots attached.

Part 2: Periplus Shopping Cart Test Documentation (Scenario A)
Test documentation:

Test Case 1: Login to Periplus Website
Field
Details
Test Case ID
TC_001
Test Case Title
Login to Periplus Website
Preconditions
User must have a valid Periplus test account
Test Steps
1. Open browser
2. Navigate to https://www.periplus.com/
3. Click on login
4. Enter valid credentials
5. Click login button
Test Data
Email: testdeletion89@gmail.com
Password: Test123!
Expected Result
User is redirected to their account dashboard or homepage with login indicator visible
Status
Pass
Comments
-


Test Case 2: Add One Product to Shopping Cart
Field
Details
Test Case ID
TC_002
Test Case Title
Add One Product to Shopping Cart
Preconditions
User must be logged in Internet connection
Test Steps
1. Use search bar to look for any book
2. Click the first result
3. Click “Add to Cart”
Test Data
Search term: “Harry Potter”
Expected Result
A confirmation appears: “Harry Potter has been added to your Shopping Cart”
Status
Pass
Comments
-



Test Case 3: Add Multiple Products to Cart
Field
Details
Test Case ID
TC_003
Test Case Title
Add Multiple Products to Cart
Preconditions
User must be logged in Internet connection
Test Steps
1. Use search bar to look for any book
2. Click the first result
3. Click “Add to Cart”
4. Use search bar to look for any book
5. Click the first result
6. Click “Add to Cart”
Test Data
Search term: “harry potter”, “game of thrones”
Expected Result
confirmation appears
Status
Pass
Comments
-



Test Case 4: Verify Product in Shopping Cart
Field
Details
Test Case ID
TC_004
Test Case Title
Verify Product in Shopping Cart
Preconditions
Product has been added previously	
Test Steps
1. Click cart icon
2. Wait for cart page to load
3. Check that product name “Harry Potter” is present in the cart
Test Data
Product: “harry potter”
Expected Result
Cart page shows product “Harry Potter” with correct title and quantity
Status
Pass
Comments
-



Test Case 5: Cart Persistence After Refresh
Field
Details
Test Case ID
TC_005
Test Case Title
Cart Persistence After Refresh
Preconditions
Product has been added previously	
Test Steps
1. Click cart icon
2. Wait for cart page to load
3. Refresh Page
3. Check that product name “Harry Potter” is present in the cart
Test Data
Product: “harry potter”
Expected Result
Cart page still shows product “Harry Potter” with correct title and quantity
Status
Pass
Comments
-


Test Case 6: Delete Product from Cart
Field
Details
Test Case ID
TC_006
Test Case Title
Delete Product From Cart
Preconditions
Product has been added previously	
Test Steps
1. Click cart icon
2. Wait for cart page to load
3. Remove a product from the cart
Test Data
Product: “harry potter”
Expected Result
“harry potter” removed from the cart
Status
Pass
Comments
-


