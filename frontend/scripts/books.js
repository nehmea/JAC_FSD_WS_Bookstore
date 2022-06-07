// global variables
const HOST = "http://localhost:8045/books"
const book_columns = [{
  field: 'id',
  title: 'Item ID',
  sortable: true,
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'isbn13',
  title: 'ISBN 13',
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'isbn10',
  title: 'ISBN 10',
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'title',
  title: 'Title',
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'language',
  title: 'Language',
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'binding',
  title: 'Format'
}, {
  field: 'release_date',
  sortable: true,
  title: 'Publication Date'
}, {
  field: 'pages',
  sortable: true,
  title: 'Pages'
}, {
  field: 'dimensions',
  title: 'Dimensions'
}, {
  field: 'rating',
  sortable: true,
  title: 'Rating'
}, {
  field: 'publisher',
  title: 'Publisher'
}, {
  field: 'authors',
  title: 'Author(s)',
  filterControl: 'input',
  filterStrictSearch: false
}, {
  field: 'numberOfCopies',
  title: 'Number of copies',
  sortable: true
}, {
  field: 'edition',
  title: 'Edition',
  sortable: true
}];


/**
 * Create new table: clear target div, then create a new table to load fetched data
 */
function createTargetTable() {
  $("#targetDiv").append(
    `<table id="books_table" class="table table-striped table-hover table-sm" 
    data-show-fullscreen="true"
    data-show-columns="true"
    data-show-pagination-switch="true"
    data-pagination="true"
    data-show-columns-toggle-all="true"
    data-filter-control="true"
    data-show-search-clear-button="true">
    </table>`)
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

// get book's thumbnail from open library
function getThumbnail(isbn) {
  var img_url = $.ajax({
    url: "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json",
    async: false
  }).responseJSON
  img_url = Object.values(img_url)[0].thumbnail_url

  return img_url

}


// create one card from JSON
/**
 * 
 * @param {String} targetDiv 
 * @param {JSON} response 
 */
function createOneCardFromJSON(targetDiv, response) {
  let img_url = `https://covers.openlibrary.org/b/isbn/${response.isbn13}-L.jpg`;
  let ol_url = `https://openlibrary.org/isbn/${response.isbn13}`
  $(targetDiv).append(
    `<div class="col-sm-3 mx-auto">
      <div class="card border-dark mb-3" id="card_book_${response.id}">
      <div class="card-header text-center">
      <img src="${img_url}" alt="Cover image not available" class="card-img-top">
      <h5 class="font-weight-bold">${response.title}</h5>
      <br>
        <h6 class="card-subtitle mb-2 text-muted"><b>Book ID:</b> ${response.id}</h6>
        <h6 class="card-subtitle mb-2 text-muted"><b>ISBN-13:</b> ${response.isbn13}</h6>
        <h6 class="card-subtitle mb-2 text-muted"><b>ISBN-10:</b> ${response.isbn10}</h6>
        </div>
      <div class="card-body">
        <p class="card-text"><b>Language:</b> ${response.language}</p>
        <p class="card-text"><b>Binding:</b> ${response.binding}</p>
        <p class="card-text"><b>Publication Date:</b> ${response.release_date}</p>
        <p class="card-text"><b>Edition:</b> ${response.edition}</p>
        <p class="card-text"><b>Page:</b> ${response.pages}</p>
        <p class="card-text"><b>Dimesnions:</b> ${response.dimensions}</p>
        <p class="card-text"><b>Rating:</b> ${response.rating}</p>
        <p class="card-text"><b>Publisher:</b> ${response.publisher}</p>
        <p class="card-text"><b>Authors:</b> ${response.authors}</p>
        <p class="card-text"><b>Number of Copies:</b> ${response.numberOfCopies}</p>
        <br>
        <div class="text-center"><a href="${ol_url}" class="btn btn-secondary">See on Open Library</a></div>
        </div>
        </div>
        </div>
        `);
  //   $.each(JSON, function(key, value){
  //     $(`#card_book_${JSON.id}`).append(`<p class="card-text">${key}: ${value}</p>`)
  // });
  // $(`#card_book_${JSON.id}`).append(`</div></div>`);
}


// create multiple card from JSON
function createMultipleCardsFromJSON(JSONarray) {
  $("#targetDiv").append(`<div class="row" id="cards_div"></div>`)
  $.each(JSONarray, function (index, response) {
    createOneCardFromJSON("#cards_div", response)
  });
}

// *******************************
// Retrieve/get/fetch methods
// *******************************
/**
 * get All books
 */
function getAllBooks() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  $.ajax(
    {
      method: "get",
      url: `${HOST}`
    }
  ).done((response) => {
    createTargetTable();
    $('#books_table').bootstrapTable({
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
 * get a book by its id
 */
function getBookByID() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let id = document.getElementById('getBookByID_bookID').value;
  $.ajax(
    {
      method: "get",
      url: `${HOST}/book/id/${id}`
    }
  ).done((response) => {
    // createTargetTable();
    // $('#books_table').bootstrapTable({

    //   columns: book_columns,
    //   data: [response]
    // });
    createOneCardFromJSON("#targetDiv", response);
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

/**
 * get a book by its ISBN
 */
function getBookByISBN() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let isbn = document.getElementById('getBookByISBN_isbn').value;
  $.ajax(
    {
      method: "get",
      url: `${HOST}/book/isbn/${isbn}`
    }
  ).done((response) => {
    createOneCardFromJSON("#targetDiv", response);
    $("#targetDiv")[0].scrollIntoView();
  }).fail((response) => {
    let warning = response.responseText;
    $("#warnings").append(`<div class="alert alert-warning text-center" role="alert"><p>${warning}</p></div>`);
    $("#warnings")[0].scrollIntoView();
    console.log(warning);

  })
}

/**
 * get a book by authorname (pattern)
 */
function getBookByAuthor() {
  $("#warnings").empty();
  $("#targetDiv").empty();
  let authorName = document.getElementById('getBookByAuthor_authorName').value;
  $.ajax(
    {
      method: "get",
      url: `${HOST}/Author/${authorName}/books`
    }
  ).done((response) => {
    // createTargetTable();
    // $('#books_table').bootstrapTable({
    //   data: response
    // })
    createMultipleCardsFromJSON(response);
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
 * Save a new book to the database
 */
function saveRecord() {
  $("#warnings").empty();
  $("#targetDiv").empty();

  const form = $("#book_update_form");
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
    $('#books_table').bootstrapTable({

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
  const id = $('#book_id').val();
  const isbn = $('#isbn13').val();
  const form = $("#book_update_form");
  const json_data = JSON.stringify(convertFormToJSON(form));
  // define URL based on provided id or isbn
  if (id == "" && isbn == "") {
    $("#warnings").append(
      `<div class="alert alert-warning text-center" role="alert">
        <p>Please supply a book ID or ISBN to update a record</p>
        </div>`);
    $("#warnings")[0].scrollIntoView();
    throw new Error("Please supply a book ID or ISBN to update a record");
  }
  if (id) {
    url = `${HOST}/book/id/${id}`;
  } else if (isbn) {
    url = `${HOST}/book/isbn/${isbn}`;
  }

  // call controller
  $.ajax(
    {
      method: "put",
      url: url,
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
    $('#books_table').bootstrapTable({

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
function deleteRecord(element) {
  $("#warnings").empty();
  $("#targetDiv").empty();

  // define url
  if (element.id == "btn_deleteBookByID") {
    const id = $('#book_id').val();
    url = `${HOST}/book/id/${id}`;
  }
  if (element.id == "btn_deleteBookByISBN") {
    const isbn = $('#book_isbn').val();
    url = `${HOST}/isbn/isbn/${id}`;
    if (isbn.length() != 10 && isbn.length() != 13) {
      $("#warnings").append(
        `<div class="alert alert-warning text-center" role="alert">
          <p>ISBN should be 10 or 13 digits long</p>
          </div>`);
      $("#warnings")[0].scrollIntoView();
      throw new Error("ISBN should be 10 or 13 digits long");
    }
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