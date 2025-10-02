        window.onload = function() {
            var messageDiv = document.querySelector(".message-container");
            if (messageDiv) {
                messageDiv.style.display = 'block';


                setTimeout(function() {
                    messageDiv.style.opacity = '0';
                    setTimeout(function() {
                        messageDiv.style.display = 'none';
                    }, 500);
                }, 2000);
            }
        };