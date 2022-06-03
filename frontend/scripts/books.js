const HOST = "http://localhost:8045"

function createAccount() {
    const email = document.getElementById("user-email").value;
    const password = document.getElementById("user-password").value;
    const name = document.getElementById("user-name").value;
    const age = document.getElementById("user-age").value;

    $.ajax({
        method: "post",
        url: `${HOST}/user`,
        data: JSON.stringify({
            "email": email,
            "password": password,
            "name": name,
            "age": age
        }),
        headers: {
            "Accept": "application/json",
            "Content-type": "application/json"
        }
    }).done((response) => {
        alert("user created");
        
    }).fail((obj, textStatus) => {
        if(obj && obj.responseJSON && obj.responseJSON.message) {
            alert(obj.responseJSON.message);
        }
        if(obj && obj.responseText) {
            alert(obj.responseText);
        }
    })
}
