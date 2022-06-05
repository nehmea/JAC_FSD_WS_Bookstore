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
    $("#targetDiv").prepend(`<table id="books_table" class="table table-striped table-hover table-sm"></table>`)
        console.log($("#targetDiv table"));
  }

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
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
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
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
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
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
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
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
    })
}


  // *******************************
  // save/post methods
  // *******************************
  function saveRecord() {
    const form = $("#book_update_form");
    console.log(form)
    const json_data = JSON.stringify(convertFormToJSON(form));
    console.log(json_data)
    $.ajax(
      {
        method: "post",
        url: `${HOST}`,
        data: JSON.stringify(json_data),
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json"
        }
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
  }).fail((obj, textStatus) => {
    alert("failed")
      if(obj && obj.responseJSON && obj.responseJSON.message) {
          alert(obj.responseJSON.message);
      }
      if(obj && obj.responseText) {
          alert(obj.responseText);
      }
  })
   
}

  
  // *******************************
  // update/modify/put methods
  // *******************************
  function updateRecord() {
    const form = $("#book_update_form");
    const json_data = convertFormToJSON(form);
    $.ajax(
      {
        method: "put",
        url: `${HOST}/book/id/`,
        data: json_data.json,
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json"
        }
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
  }).fail((obj, textStatus) => {
      if(obj && obj.responseJSON && obj.responseJSON.message) {
          alert(obj.responseJSON.message);
      }
      if(obj && obj.responseText) {
          alert(obj.responseText);
      }
  })
   
}