const days = document.getElementById('days');
const hours = document.getElementById('hours');
const minutes = document.getElementById('minutes');
const seconds = document.getElementById('seconds');
var trackname = document.getElementById('trackname');
const racedate = document.getElementById('racedate');
const fullname = document.getElementById('full-name');
const trackconfig = document.getElementById('track-config');
const trackimg = document.getElementById('track-img');

var tab = [
    ['April 24 2022 20:00:00', 'Suzuka' , 'Suzuka International Racing Course', 'Grand Prix Course', 'https://simracingwiki.com/images/1/11/SuzukaConfig1.png', 1],
    ['May 1 2022 20:00:00', 'Monza', 'Autodromo Nazionale Monza', 'Grand Prix', 'https://simracingwiki.com/images/e/e7/MonzaConfig1.png', 2],
    ['May 15 2022 20:00:00', 'Spa', 'Circuit de Spa-Francorchamps', 'Grand Prix Pits', 'https://simracingwiki.com/images/b/ba/SpaConfig1.png', 3],
    ['May 2 2022 20:00:00', 'Zandvoort', 'Circuit Park Zandvoort', 'Grand Prix', 'https://simracingwiki.com/images/4/4f/ZandvoortConfig1.png', 4],
    ['May 29 2022 20:00:00', 'Hungaroring', 'Hungaroring', 'Grand Prix', 'https://simracingwiki.com/images/4/43/HungaroringGP.png', 5],
    ['November 12 2023 20:00:00', 'Watkins Glen', 'Watkins Glen International', 'Classic Boot', 'https://simracing.wiki/mediawiki-1.39.1/images/c/c2/WatkinsGlenConfig1.png', 6]
]

let newYearTime = new Date(tab[0][0]);
var track = tab[0][1];
let race = newYearTime;
var fname = tab[0][2];
var tconfig = tab[0][3];
var timage = tab[0][4];
var activeel = document.getElementById(tab[0][5]);

function updateCountDown() {

    const currentTime = new Date();

    for (let i = 0; i < tab.length; i++) {
        if (currentTime - newYearTime >= 0) {
            newYearTime = new Date(tab[i][0]);
        }
    }

    const diff = newYearTime - currentTime;

    const d = Math.floor(diff / 1000 / 60 / 60 / 24);
    const h = Math.floor(diff / 1000 / 60 / 60) % 24;
    const m = Math.floor(diff / 1000 / 60) % 60;
    const s = Math.floor(diff / 1000) % 60;

    days.innerHTML = d;
    hours.innerHTML = h < 10 ? '0' + h : h;
    minutes.innerHTML = m < 10 ? '0' + m : m;
    seconds.innerHTML = s < 10 ? '0' + s : s;
}

setInterval(updateCountDown, 1000);

function updateTrack() {

    const currentTime = new Date();

    for (let i = 0; i < tab.length; i++) {
        if (currentTime - newYearTime >= 0) {
            newYearTime = new Date(tab[i][0]);
            track = tab[i][1];
            fname = tab[i][2];
            tconfig = tab[i][3];
            timage = tab[i][4];
            activeel = document.getElementById(tab[i][5]);
        }
    }

    race = (newYearTime.getDate() < 10 ? '0' : '') + newYearTime.getDate() + '.' + (newYearTime.getMonth() < 10 ? '0' : '') + (newYearTime.getMonth() + 1) + '.' + newYearTime.getFullYear()
        + ' ' + newYearTime.getHours() + ':' + (newYearTime.getMinutes() < 10 ? '0' : '') + newYearTime.getMinutes(2);

    trackname.innerHTML = track;
    racedate.innerHTML = race;
    fullname.innerHTML = fname;
    trackconfig.innerHTML = 'Konfiguracja: ' + tconfig;
    trackimg.setAttribute('src', timage);
    activeel.classList.add('active');
}

setInterval(updateCountDown, 1000);
updateTrack();