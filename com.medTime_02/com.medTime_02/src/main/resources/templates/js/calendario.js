const currentDate = document.querySelector(".data-atual"),
daysTag = document.querySelector(".dias");
prevNextIcon = document.querySelectorAll(".icones span");

let date = new Date(),
currYear = date.getFullYear(),
currMouth = date.getMonth();

const mouths = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

const renderCalendario = () => {
    let firstDateofMonth = new Date(currYear, currMouth, 1).getDay();
    lastDateofMouth = new Date(currYear, currMouth + 1, 0).getDate();
    lastDayofMouth = new Date(currYear, currMouth, lastDateofMouth).getDay();
    lasDateofLastMouth = new Date(currYear, currMouth, 0).getDate();
    let liTag = "";

    for (let i = firstDateofMonth; i > 0; i--) {
        liTag += `<li class="inativo">${lasDateofLastMouth - i + 1}</li>`;
    }

    for (let i = 1; i <= lastDateofMouth; i++) {
        let isToday = i === date.getDate() && currMouth === new Date().getMonth()
                      && currYear === new Date().getFullYear() ? "ativo" : "";
        liTag += `<li class"${isToday}">${i}</li>`;
    }

    for (let i = lastDayofMouth; i < 6; i++) {
        liTag += `<li class="inativo">${i - lastDayofMouth + 1}</li>`;;
        
    }

    currentDate.innerText = `${mouths[currMouth]} ${currYear}`;
    daysTag.innerHTML = liTag;
}
renderCalendario ();

prevNextIcon.forEach(icon => {
    icon.addEventListener("click", () =>{
        currMouth = icon.id === "anterior" ? currMouth - 1 : currMouth + 1;
        renderCalendario ();
    });
});