const burger = document.querySelector(".burger");
const iconBurger = document.querySelector(".fa-bars");
const iconExit = document.querySelector(".fa-times");
const nav = document.querySelector(".categories");
const socials = document.querySelector(".socials-mob");
const dropdown = document.querySelector(".dropdown");
const angle = document.querySelector(".fa-angle-down");
const drop = document.querySelector(".dropdown-ul");

if (burger) {
    burger.addEventListener("click", function () {
        iconBurger.classList.toggle("show");
        iconExit.classList.toggle("show");
        nav.classList.toggle("show");
        socials.classList.toggle("show");
    })
}
if (dropdown) {
    dropdown.addEventListener("click", function () {
        drop.classList.toggle("active");
    })
}
