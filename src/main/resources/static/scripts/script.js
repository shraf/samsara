var username = document.querySelector("#username");
var password = document.querySelector("#password");
var repassword = document.querySelector("#repassword");
var email = document.querySelector("#email");
var erormsg = document.querySelector("#erormsg");
var btnsubmit = document.querySelector("#btnsubmit");
var xhr = new XMLHttpRequest();
btnsubmit.addEventListener("click", validate);

function validate(evt) {
  let params = `username=${username.value}&password=${password.value}&repassword=${repassword.value}&email=${email.value}`;
  xhr.open("POST", "https://localhost:8080/sign/validsignup", true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(params);
  xhr.onreadystatechange = function() {
    console.log(params);
    console.log(this.responseText);
	console.log("ahoo");
	console.log(this);
    if (username.value.length == 0) {
      erormsg.textContent = "اسم المستخدم غير صالح";
      return;
    }
    if (this.responseText == "false") {
      evt.preventDefault();
      if (password.value != repassword.value || password.value.length == 0)
        erormsg.textContent = "كلمة السر غير متطابقة";
      else if (!validateEmail(email.value))
        erormsg.textContent = "البريد الالكترونى غير صالح";
      else erormsg.textContent = "اسم المستخدم غير صالح";
    } else if (this.responseText == "true") {
      document.querySelector(".signup-form").submit();
    }
  };
}

function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}
