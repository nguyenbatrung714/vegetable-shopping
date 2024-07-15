$(document).ready(function () {
    $('#submit').click(function () {
        let user = {
            username: $('#username').val(),
            email: $('#email').val(),
            password: $('#password').val()
        }
        axios.post(`http://localhost:8080/api/v1/auth`, user)
            .then(function (response) {
                Swal.fire({
                    title: 'Register',
                    text: 'Register successfully',
                    icon: 'success',
                    button: 'OK'
                })
            })
            .catch(function (error) {
                console.log(error);
                Swal.fire({
                    title: 'Register Failed',
                    text: JSON.stringify(error.response.data),
                    icon: 'error',
                    button: 'OK'
                });
            })
    })
});