function get(path) {
    axios.get(path).then(function (response) {
        if (response.data == 'successful') {
            swal("Good job", "added successfully", "success");
        } else {
            swal("Error!!", "Something went wrong try again...", "error");
        }
    });
}

function get(path, JSONString) {
    axios.get(path).then(function (response) {
        if (response.data == 'successful') {
            swal("Good job", "added successfully", "success");
        } else {
            swal("Error!!", "Something went wrong try again...", "error");
        }
    });
}

function post(path) {
    axios.get(path).then(function (response) {
        if (response.data == 'successful') {
            swal("Good job", "added successfully", "success");
        } else {
            swal("Error!!", "Something went wrong try again...", "error");
        }
    });
}

function post(path, JSONString) {
    axios.get(path).then(function (response) {
        if (response.data == 'successful') {
            swal("Good job", "added successfully", "success");
        } else {
            swal("Error!!", "Something went wrong try again...", "error");
        }
    });
}

