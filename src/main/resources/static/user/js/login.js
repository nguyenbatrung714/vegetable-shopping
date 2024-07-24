$(document).ready(function () {
    $('#login-normal').click(function () {
        let user = {
            username: $('#login-username').val(),
            password: $('#login-password').val()
        };
        axios.post('http://localhost:8080/api/v1/auth/login', user)
            .then(async function (response) {
                sessionStorage.setItem('token', response.data);

                let username = parseJwt(response.data).sub;
                let account = await findUser(username);
                sessionStorage.setItem('account', JSON.stringify(account));

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
                    text: JSON.stringify(error.config.data),
                    icon: 'error',
                    button: 'OK'
                });
            });
    });
});

function parseJwt(token)  {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
}

async function findUser(username) {
    try {
        const response = await axios.get(`http://localhost:8080/api/v1/auth?username=${username}`);
        return response.data;
    } catch (error) {
        console.error(error);
        return null;
    }
}

