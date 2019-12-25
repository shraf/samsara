var username = document.querySelector("#username");
var password = document.querySelector("#password");
var repassword = document.querySelector("#repassword");
var email = document.querySelector("#email");
var erormsg = document.querySelector("#erormsg");
var btnsubmit = document.querySelector("#btnsubmit");
var btnlogin = document.querySelector("#btnlogin");
var xhr = new XMLHttpRequest();
/* let list = document.querySelector(".list-group");
             let listelement = ["sfd", "fdsf", "adsafadsf"];
             
             list.style.display = "none";
             function display_toggle() {
             if (!list.classList.contains("show-list")) {
             list.classList.add("show-list");
             } else {
             list.classList.remove("show-list");
             console.log("g");
             }
             }*/
var url_string = window.location.href;
var url = new URL(url_string);
var id = url.searchParams.get("id");
console.log("ahoso");
console.log("shraf");
function validate(evt) {
  let params = `username=${username.value}&password=${password.value}&repassword=${repassword.value}&email=${email.value}`;
  xhr.open("POST", "./sign/validsignup", true);
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
function validatelogin(evt) {
  evt.preventDefault();
  console.log("bdana");
  let params = `username=${username.value}&password=${password.value}`;
  xhr.open("POST", "./sign/validlogin", true);
  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhr.send(params);
  xhr.onload = function() {
    console.log(params);
    console.log(this.responseText);
    if (this.responseText == "false") {
      console.log("a7aaaaaaaaaaaaaaaaa");
      erormsg.textContent = "اتأكد من صحة معلوماتك ينجم";
    } else if (this.responseText == "true") {
      console.log("adfdslfdsfdlaaah");
      document.querySelector(".signin-form").submit();
    }
  };
}
