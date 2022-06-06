// global variables
const HOST = "http://localhost:8045/books"
const book_columns = [{
    field: 'id',
    title: 'Item ID',
  }, {
    field: 'isbn13',
    title: 'ISBN 13'
  }, {
    field: 'isbn10',
    title: 'ISBN 10'
  }, {
    field: 'title',
    title: 'Title'
  }, {
    field: 'language',
    title: 'Language'
  }, {
    field: 'binding',
    title: 'Format'
  }, {
    field: 'release_date',
    title: 'Publication Date'
  }, {
    field: 'pages',
    title: 'Pages'
  }, {
    field: 'dimensions',
    title: 'Dimensions'
  }, {
    field: 'rating',
    title: 'Rating'
  }, {
    field: 'publisher',
    title: 'Publisher'
  }, {
    field: 'authors',
    title: 'Author(s)'
  }, {
    field: 'numberOfCopies',
    title: 'Number of copies'
  }, {
    field: 'edition',
    title: 'Edition'
  }];


  /**
   * Create new table: clear target div, then create a new table to load fetched data
   */
  function createTargetTable() {
      $("#targetDiv").empty();
    $("#targetDiv").append(`<table id="books_table" class="table table-striped table-hover table-sm"></table>`)
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
   * get All books
   */
function getAllBooks() {
    $.ajax(
        {
            method: "get",
            url: `${HOST}`
        }
    ).done((response) => {
        createTargetTable();
        $('#books_table').bootstrapTable({
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
 * get a book by its id
 */
function getBookByID() {
    let id = document.getElementById('getBookByID_bookID').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/book/id/${id}`
        }
    ).done((response) => {
        createTargetTable();
        $('#books_table').bootstrapTable({
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
 * get a book by its ISBN
 */
function getBookByISBN() {
    let id = document.getElementById('getBookByISBN_isbn').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/book/isbn/${id}`
        }
    ).done((response) => {
        createTargetTable();
        $('#books_table').bootstrapTable({
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
 * get a book by authorname (pattern)
 */
function getBookByAuthor() {
    let authorName = document.getElementById('getBookByAuthor_authorName').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/Author/${authorName}/books`
        }
    ).done((response) => {
        createTargetTable();
        $('#books_table').bootstrapTable({
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


  // *******************************
  // save/post methods
  // *******************************
  /**
   * Save a new book to the database
   */
  function saveRecord() {
    $("#warnings").empty();
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
    // declare variables
    const id = $('#book_id').val();
    const isbn = $('#isbn13').val();
    const form = $("#book_update_form");
    const json_data = JSON.stringify(convertFormToJSON(form));
    // define URL based on provided id or isbn
    if(id==""  && isbn=="" ) {      
      $("#warnings").append(
        `<div class="alert alert-warning text-center" role="alert">
        <p>Please supply a book ID or ISBN to update a record</p>
        </div>`);
        $("#warnings")[0].scrollIntoView();
        throw new Error("Please supply a book ID or ISBN to update a record");
    }
    if(id) {
      url = `${HOST}/book/id/${id}`;
    } else if(isbn) {
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
  function deleteRecord(element) {
    console.log(element.id)
    $("#warnings").empty();

    // define url
    if(element.id == "btn_deleteBookByID") {
      const id = $('#book_id').val();
      url = `${HOST}/book/id/${id}`;
    }
    if(element.id == "btn_deleteBookByISBN") {
      const isbn = $('#book_isbn').val();
      url = `${HOST}/isbn/isbn/${id}`;
      if(isbn.length() !=10 && isbn.length() !=13) {      
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