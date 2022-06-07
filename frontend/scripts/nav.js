/**
 * Developed by: Ali Nehme
 * Developed on: June 7, 2022
 */

var navbar =
    `<nav>
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link" href="../index.html">Home</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"
                    aria-expanded="false">Books</a>
                <ul class="dropdown-menu">
                    <li><a href="">Fetch</a></li>
                    <li><a class="dropdown-item" href="books-fetch.html#getAllBooks">List All Books</a></li>
                    <li><a class="dropdown-item" href="books-fetch.html#getBookByID">Get a Book by ID</a></li>
                    <li><a class="dropdown-item" href="books-fetch.html#getBookByISBN">Get a Book by ISBN</a>
                    </li>
                    <li><a class="dropdown-item" href="books-fetch.html#getBookByAuthor">Get a Book by Author
                            Name</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    <li><a href="books-update.html">Update</a></li>
            </li>
            <li><a class="dropdown-item" href="books-update.html">Update a Book by ID</a></li>
            <li><a class="dropdown-item" href="books-update.html">Update a Book by ISBN</a></li>
            <li>
                <hr class="dropdown-divider">
            <li><a href="books-update.html">Save</a></li>
            </li>
            <li><a class="dropdown-item" href="books-update.html">Save a New Book</a></li>
            <li>
                <hr class="dropdown-divider">
            <li><a href="books-delete.html">Delete</a></li>
            </li>
            <li><a class="dropdown-item" href="books-delete.html#deleteBookByID">Delete a Book by ID</a></li>
            <li><a class="dropdown-item" href="books-delete.html#deleteBookByISBN">Delete a Book by ISBN</a>
            </li>
        </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"
                aria-expanded="false">Customers</a>
            <ul class="dropdown-menu">
                <li><a href="customers-fetch.html">Fetch</a></li>
                <li><a class="dropdown-item" href="customers-fetch.html#getAllCustomers">List All Customers</a>
                </li>
                <li><a class="dropdown-item" href="customers-fetch.html#getCustomerByID">Get a Customer by
                        ID</a></li>
                <li><a class="dropdown-item" href="customers-fetch.html#getCustomerByInfo">Get a Customer by
                        Information</a></li>
                <li>
                    <hr class="dropdown-divider">
                <li><a href="customers-update.html">Update</a></li>
        </li>
        <li><a class="dropdown-item" href="customers-update.html">Update a Customer by ID</a></li>
        <li>
            <hr class="dropdown-divider">
        <li><a href="customers-update.html">Save</a></li>
        </li>
        <li><a class="dropdown-item" href="customers-update.html">Save a New Customer</a></li>
        <li>
            <hr class="dropdown-divider">
        <li><a href="customer-delete.html">Delete</a></li>
        </li>
        <li><a class="dropdown-item" href="customer-delete.html">Delete a Customer by ID</a></li>
        </ul>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"
                aria-expanded="false">Loans</a>
            <ul class="dropdown-menu">
                <li><a href="loans-fetch.html">Fetch</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getAllLoans">List All Loans</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLoanById">Get a Loan by ID</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLoanByCustomerId">Get Loans by Customer
                        ID</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLoanByBookId">Get Loans by Book ID</a>
                </li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLoanByBookISBN">Get Loans by Book
                        ISBN</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLastLoanByCustomerId">Get Last Loan of a
                        Customer</a></li>
                <li><a class="dropdown-item" href="loans-fetch.html#getLastLoanByBookId">Get Last Loan of a
                        Book</a></li>
                <li>
                    <hr class="dropdown-divider">
                    <a href="loans-update.html">Update</a>
                </li>
                <li><a class="dropdown-item" href="loans-update.html">Update a Loan by ID</a></li>
                <li>
                    <hr class="dropdown-divider">
                <li><a href="loans-update.html">Save</a></li>
        </li>
        <li><a class="dropdown-item" href="loans-update.html">Save a New Loan</a></li>
        <li>
            <hr class="dropdown-divider">
        <li><a href="loans-delete.html">Delete</a></li>
        </li>
        <li><a class="dropdown-item" href="loans-delete.html">Delete a Loan by ID</a></li>
        </ul>
        </li>
        </ul>
    </nav>
    <br>
    <div id="warnings"></div>`


$("#header").append(navbar);

// append targetDiv to end of body
$('body').append(`</br></br><div class="d-grid gap-2 col-10 mx-auto" id="targetDiv"></div>`)

// append link to styles to head
$('head').append(
    `<!-- styles -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.css">

    <!-- custome style -->
    <link rel="stylesheet" href="../css/style.css">`
)

// append external scripts to end of body
$('#external_scripts').append(
    `<script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    <script src="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.js"></script>
    <script
        src="https://unpkg.com/bootstrap-table@1.20.2/dist/extensions/filter-control/bootstrap-table-filter-control.min.js"></script>`
)