const HOST = "http://localhost:8045/books"

function getAllBooks() {
    $.ajax(
        {
            method: "get",
            url: `${HOST}`
        }
    ).done((response) => {

        $('#table').bootstrapTable({
            pagination: true,
            search: true,
            columns: [{
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
              }],
              data: response
            
        });
       document.getElementById('targetDiv').append(JSON.stringify(response, undefined, 2));
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

function getBookByID() {
    let id = document.getElementById('getBookByID_bookID').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/book/id/${id}`
        }
    ).done((response) => {
       document.getElementById('targetDiv').append(response);
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
    })
}

function getBookByISBN() {
    let id = document.getElementById('getBookByISBN_isbn').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/book/isbn/${id}`
        }
    ).done((response) => {
       document.getElementById('targetDiv').append(response);
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
    })
}

function getBookByAuthor() {
    let id = document.getElementById('getBookByAuthor_authorName').value;
    $.ajax(
        {
            method: "get",
            url: `${HOST}/book/isbn/${id}`
        }
    ).done((response) => {
       document.getElementById('targetDiv').append(response);
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
    })
}

