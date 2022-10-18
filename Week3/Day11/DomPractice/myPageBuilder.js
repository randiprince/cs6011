"use strict";

function createTopDiv() {
    let div = document.createElement('div');
    let para = document.createElement('p');
    para.innerText = "this div contains a p tag, h1 tag, and a button!";
    div.setAttribute("style", "font-size:24px; color:blue");
   
    let h1 = document.createElement('h1');

    h1.innerText = "hey i made a div! this is an h1";
    h1.setAttribute("style", "color:green");

    let button = document.createElement("button");
    button.innerText = "Click me!"
    button.setAttribute("style", "padding: 10px; font-size: 16px;");
    button.setAttribute("onclick", "myClick()")

    div.appendChild(h1);
    div.appendChild(para);
    div.appendChild(button);
    document.body.append(div);
    
}

function createDivTwo() {
    let div = document.createElement('div');
    div.innerText = "hey i made another div!";
    div.setAttribute("style", "font-size:20px; color:lightblue; padding: 50px;");
    document.body.append(div);
    let orderedList = document.createElement('ol');
    let listtItemOne = document.createElement('li')
    let listtItemTwo = document.createElement('li')
    orderedList.innerText = "My dogs names are:";
    listtItemOne.innerText = "Hugo";
    listtItemTwo.innerText = "Squishy";
    orderedList.appendChild(listtItemOne);
    orderedList.appendChild(listtItemTwo);
    div.appendChild(orderedList);
    document.body.append(div);
}

function myClick() {
    alert("you clicked me!");
}

createTopDiv();
createDivTwo();
