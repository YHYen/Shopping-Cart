// function includeHTML() {
//   /* Loop through a collection of all HTML elements: */
//   var elements = document.getElementsByTagName("*");
//   for (let i = 0; i < elements.length; i++) {
//     let element = elements[i];
//     /*search for elements with a certain atrribute:*/
//     let file = element.getAttribute("include-html");
//     if (file) {
//       /* Make an HTTP request using the attribute value as the file name: */
//       let xHttp = new XMLHttpRequest();
//       xHttp.onreadystatechange = function () {
//         if (this.readyState == 4) {
//           if (this.status == 200) { element.innerHTML = this.responseText; }
//           if (this.status == 404) { element.innerHTML = "Page not found."; }
//           /* Remove the attribute, and call this function once more: */
//           element.removeAttribute("include-html");
//           includeHTML();
//         }
//       }
//       xHttp.open("GET", file, true);
//       xHttp.send();
//       /* Exit the function: */
//       return;
//     }
//   }
// }

// function login() {
//   let loginBtn = document.getElementById("loginBtn");
//   let usernameBtn = document.getElementById("username");
//   let userSubList = document.getElementById("userSubList");
//   loginBtn.style.display = 'none';
//   usernameBtn.style.display = 'block';
//   userSubList.style.display = 'block';
// }

// function logout() {
//   let loginBtn = document.getElementById("loginBtn");
//   let usernameBtn = document.getElementById("username");
//   let userSubList = document.getElementById("userSubList");
//   loginBtn.style.display = 'block';
//   usernameBtn.style.display = 'none';
//   userSubList.style.display = 'none';
// }