const HOST = "http://localhost:8045/books"

function getAllBooks() {
    $.ajax(
        {
            method: "get",
            url: `${HOST}`
        }
    ).done((response) => {
       document.getElementById('targetDiv').append(JSON.stringify(response, undefined, 2));
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

