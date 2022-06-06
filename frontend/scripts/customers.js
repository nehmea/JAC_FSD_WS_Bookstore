// global variables
const HOST = "http://localhost:8045/customers"
const book_columns = [{
  field: 'id',
  title: 'Item ID',
}, {
  field: 'firstName',
  title: 'First Name'
}, {
  field: 'middleName',
  title: 'Middle Name'
}, {
  field: 'lastName',
  title: 'Last Name'
}, {
  field: 'dob',
  title: 'Date of Birth'
}, {
  field: 'address',
  title: 'Address'
}, {
  field: 'city',
  title: 'City'
}, {
  field: 'state',
  title: 'State'
}, {
  field: 'zipcode',
  title: 'Zip Code'
}, {
  field: 'phone',
  title: 'Phone Number'
}, {
  field: 'registrationDate',
  title: 'Registration Date'
}];


/**
 * Create new table: clear target div, then create a new table to load fetched data
 */
 function createTargetTable() {
  $("#targetDiv").append(`<table id="customer_table" class="table table-striped table-hover table-sm"></table>`)
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
 * get All customers
 */
function getAllCustomers() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  $.ajax(
    {
      method: "get",
      url: `${HOST}`
    }
  ).done((response) => {
    createTargetTable();
    $('#customer_table').bootstrapTable({
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
 * get a customer by its id
 */
function getCustomerByID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('customerID').value;
  $.ajax(
    {
      method: "get",
      url: `${HOST}/customer/id/${id}`
    }
  ).done((response) => {
    createTargetTable();
    $('#customer_table').bootstrapTable({
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
 * get a customer by its first name, last name, and dob
 */
function getCustomerByInfo() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let firstName = document.getElementById('getCustomerByInfo_firstName').value;
  let lastName = document.getElementById('getCustomerByInfo_lastName').value;
  let dob =  document.getElementById('getCustomerByInfo_dob').value;
  $.ajax(
    {
      method: "post",
      url: `${HOST}/customer`,
      data: JSON.stringify({
        "firstName": firstName,
        "lastName": lastName,
        "dob": dob
    }),
    headers: {
      "Accept": "application/json",
      "Content-type": "application/json"
    }
    }
  ).done((response) => {
    createTargetTable();
    $('#customer_table').bootstrapTable({
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

  const form = $("#customer_update_form");
  const json_data = JSON.stringify(convertFormToJSON(form));
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
    $('#customer_table').bootstrapTable({
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
 * detects if id or isbn used to modify corresponding book
 */
function updateRecord() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  // declare variables
  const id = $('#id').val();
  const form = $("#customer_update_form");
  const json_data = JSON.stringify(convertFormToJSON(form));
  // define URL based on provided id or isbn
  if (id == "") {
    $("#warnings").append(
      `<div class="alert alert-warning text-center" role="alert">
        <p>Please supply a Customer ID to update a record</p>
        </div>`);
    $("#warnings")[0].scrollIntoView();
    throw new Error("Please supply a Customer ID to update a record");
  }

  // call controller
  $.ajax(
    {
      method: "put",
      url: `${HOST}/customer/id/${id}`,
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
    $('#customer_table').bootstrapTable({
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
  const id = $('#customer_id').val();
  url = `${HOST}/customer/id/${id}`;
  if (id == "") {
      $("#warnings").append(
        `<div class="alert alert-warning text-center" role="alert">
          <p>Please provide a customer id</p>
          </div>`);
      $("#warnings")[0].scrollIntoView();
      throw new Error("Please provide a customer id");
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
      <p>Record has been deleted</p>
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