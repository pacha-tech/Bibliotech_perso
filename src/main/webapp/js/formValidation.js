document.addEventListener("DOMContentLoaded", function () {
    const tel = document.getElementById("tel_num");
    const email = document.getElementById("email");
    const telError = document.getElementById("tel-error");
    const emailError = document.getElementById("email-error");

    const telRegex = /^6[0-9]{8}$/;
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$/;

    tel.addEventListener("input", function (event) {
        const value = event.target.value.trim();
        if (value === "") {
            telError.textContent = "";
        } else if (!telRegex.test(value)) {
            telError.textContent = "Format attendu : 6xxxxxxxx (9 chiffres).";
        } else {
            telError.textContent = "";
        }
    });

    email.addEventListener("input", function (event) {
        const value = event.target.value.trim();
        if (value === "") {
            emailError.textContent = "";
        } else if (!emailRegex.test(value)) {
            emailError.textContent = "Format attendu : exemple@gmail.com";
        } else {
            emailError.textContent = "";
        }
    });

    document.querySelector(".add-user-form").addEventListener("submit", function (e) {
        let hasError = false;

        if (!telRegex.test(tel.value.trim())) {
            telError.textContent = "Le numéro doit être au format 6xxxxxxxx (9 chiffres).";
            tel.focus();
            hasError = true;
        }

        if (!emailRegex.test(email.value.trim())) {
            emailError.textContent = "L'adresse email doit être au format correct (ex. mike@gmail.com).";
            if (!hasError) email.focus();
            hasError = true;
        }

        if (hasError) e.preventDefault();
    });
});
