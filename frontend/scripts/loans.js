// global variables
const HOST = "http://localhost:8045/loans"
const book_columns = [{
  field: 'id',
  title: 'Loan ID',
}, {
  field: 'customerId',
  title: 'Customer ID'
}, {
  field: 'bookId',
  title: 'Book ID'
}, {
  field: 'dateOut',
  title: 'Date Out'
}, {
  field: 'dateIn',
  title: 'Date In'
}];


/**
 * Create new table: clear target div, then create a new table to load fetched data
 */
 function createTargetTable() {
  $("#targetDiv").append(`<table id="loan_table" class="table table-striped table-hover table-sm"></table>`)
  // console.log($("#targetDiv table"));
}

/**
 * A method that converts form input into a JSON object
 * @param {*} form 
 * @returns 
 */
function convertFormToJSON(form) {
  const array = $(form).serializeArray(); // Encodes the set of form elements as an array of names and values.
  const json = {};
  $.each(array, function () {
    json[this.name] = this.value || null;
  });
  return json;
}



// *******************************
// Retrieve/get/fetch methods
// *******************************
/**
 * get All loans
 */
function getAllLoans() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  $.ajax(
    {
      method: "get",
      url: `${HOST}`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: response

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

/**
 * get a loan by its id
 */
function getLoanByID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLoanByID_loanID').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/loan/id/${id}`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

/**
 * get a loan by customer ID
 */
 function getLoanByCustomerID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLoanByCustomerId_customerID').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/customer/id/${id}`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: response

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}


/**
 * get a loan by book ID
 */
 function getLoanByBookID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLoanByBookId_bookID').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/book/id/${id}`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: response

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

/**
 * get a loan by book ISBN
 */
 function getLoanByBookISBN() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLoanByBookISBN_bookISBN').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/book/isbn/${id}`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: response

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}


/**
 * get last loan by customer ID
 */
 function getLastLoanByCustomerID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLastLoanByCustomerId_customerID').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/customer/${id}/last`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}


/**
 * get a loan by book ID
 */
 function getLastLoanByBookID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getLastLoanByBookId_bookID').value;
  console.log(id)
  $.ajax(
    {
      method: "get",
      url: `${HOST}/book/${id}/last`
    }
  ).done((response) => {
    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

// *******************************
// save/post methods
// *******************************
/**
 * Save a new customer to the database
 */
function saveRecord() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  const form = $("#loan_update_form");
  const json_data = JSON.stringify(convertFormToJSON(form));
  console.log(json_data)
  $.ajax(
    {
      method: "post",
      url: `${HOST}`,
      data: json_data,
      headers: {
        "Accept": "application/json",
        "Content-type": "application/json"
      }
    }
  ).done((response) => {
    $("#targetDiv").append(
      `<div class="alert alert-success text-center" role="alert">
      <p>Record saved!</p>
      </div>`);

    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]

    });
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}


// *******************************
// update/modify/put methods
// *******************************
/**
 * modify corresponding loan
 */
function updateRecord() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  // declare variables
  const id = $('#id').val();
  const form = $("#loan_update_form");
  const json_data = JSON.stringify(convertFormToJSON(form));
  console.log(json_data)
  // define URL based on provided id or isbn
  if (id == "") {
    $("#warnings").append(
      `<div class="alert alert-warning text-center" role="alert">
        <p>Please supply a Loan ID to update a record</p>
        </div>`);
    $("#warnings")[0].scrollIntoView();
    throw new Error("Please supply a Loan ID to update a record");
  }

  // call controller
  $.ajax(
    {
      method: "put",
      url: `${HOST}/loan/id/${id}`,
      data: json_data,
      headers: {
        "Accept": "application/json",
        "Content-type": "application/json"
      }
    }
  ).done((response) => {
    $("#targetDiv").append(
      `<div class="alert alert-success text-center" role="alert">
      <p>Record has been Updated</p>
      </div>`);

    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]
    })
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").empty();
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();

  })
}

/**
 * modify loan dateIn
 */
 function updateRecordDateIn() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  // declare variables
  const id = $('#loan_id').val();
  const date_in = $('#date_in').val();
  // define URL based on provided id or isbn
  if (id == "") {
    $("#warnings").append(
      `<div class="alert alert-warning text-center" role="alert">
        <p>Please supply a Loan ID to update a record</p>
        </div>`);
    $("#warnings")[0].scrollIntoView();
    throw new Error("Please supply a Loan ID to update a record");
  }

  // call controller
  $.ajax(
    {
      method: "put",
      url: `${HOST}/loan/id/${id}/date-in`,
      data: JSON.stringify(
        {
          "id" : id,
          "dateIn" : date_in
        }
      ),
      headers: {
        "Accept": "application/json",
        "Content-type": "application/json"
      }
    }
  ).done((response) => {
    $("#targetDiv").append(
      `<div class="alert alert-success text-center" role="alert">
      <p>Record has been Updated</p>
      </div>`);

    createTargetTable();
    $('#loan_table').bootstrapTable({
      pagination: true,
      search: true,
      columns: book_columns,
      data: [response]
    })
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").empty();
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();

  })
}

// *******************************
// DELETE methods
// *******************************
/**
 * detects if id or isbn used to delete corresponding book
 */
function deleteRecord() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  // define url
  const id = $('#deleteLoanById_loanID').val();
  url = `${HOST}/loan/id/${id}`;

  if (id == "") {
      $("#warnings").append(
        `<div class="alert alert-warning text-center" role="alert">
          <p>Please provide a loan id</p>
          </div>`);
      $("#warnings")[0].scrollIntoView();
      throw new Error("Please provide a loan id");
  }

  confirm("Are you sure you want to DELETE this record?")
  // call controller
  $.ajax(
    {
      method: "delete",
      url: url
    }
  ).done((response) => {
    $("#warnings").append(
      `<div class="alert alert-success text-center" role="alert">
      <p>Record deleted!</p>
      </div>`);
    $("#warnings")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"></div>`);
    $("#warnings div").append(`<p>${warning}</p>`);
    // $("#warnings").append(`<p class="text-warning text-center">${warning}</p>`);
    $("#warnings")[0].scrollIntoView();

  })
}