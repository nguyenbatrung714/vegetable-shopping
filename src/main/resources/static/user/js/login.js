$(document).ready(function () {
    $('#login-normal').click(function () {
        let user = {
            username: $('#login-username').val(),
            password: $('#login-password').val()
        };
        axios.post('http://localhost:8080/api/v1/auth/login', user)
            .then(function (response) {
                sessionStorage.setItem('token', response.data);
                Swal.fire({
                    title: 'Login',
                    text: 'Login successfully',
                    icon: 'success',
                    button: 'OK'
                }).then(() => {
                    setTimeout(() => {
                        window.location.href = 'http://localhost:8080/vegetable-shopping/home';
                    }, 1000);
                });
            })
            .catch(function (error) {
                console.log('Error:', error);
                Swal.fire({
                    title: 'Login Failed',
                    text: 'An error occurred while logging in',
                    icon: 'error',
                    button: 'OK'
                });
            });
    });
});