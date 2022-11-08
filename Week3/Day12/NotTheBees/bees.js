'use strict';

let myCanvas = document.querySelector('canvas');
myCanvas.style = "border: 5px solid orange; padding 20px;";
myCanvas.height = window.innerHeight;
myCanvas.width = window.innerWidth;


let context = myCanvas.getContext('2d'); // gets object with 2D drawing methods
let bees = [];

let honeyPot = new Image();
honeyPot.src = "honey-pot.png";

for (let i = 0; i < 10; i++) {
    let bee = {}; // make bee obj
    bee.img = new Image();
    bee.img.src = "bee.png"; // add and set img
    bee.xPos = Math.floor(Math.random() * 20) * 50;
    bee.yPos = Math.floor(Math.random() * 10) * 100; // have bee start at random position
    bees[i] = bee; // add bee to array
}

// window.console.log(bees.length);


function handleMouseMove(e) {
    let pos = getMousePos(myCanvas, e);
    honeyPot.xPos = pos.x;
    honeyPot.yPos = pos.y;
   
}
document.addEventListener('mousemove', handleMouseMove);

function getMousePos(canvas, evt) {
    let rect = canvas.getBoundingClientRect();
    return {
      x: evt.clientX - rect.left,
      y: evt.clientY - rect.top
    };
  }

function updateBeePosition() {
    let scale = 2;

    for ( let i = 0; i < 4; i++) {
        if (honeyPot.xPos - bees[i].xPos > 0)
        {
            bees[i].xPos += 1*scale;
        } else {
            bees[i].xPos -= 1*scale;
        }

        if (honeyPot.yPos - bees[i].yPos > 0) {
            bees[i].yPos += 1.5*scale;
        } else{
            bees[i].yPos -= 1.5*scale;
        }
    }
    for ( let i = 4; i < 6; i++) {
        if (honeyPot.xPos - bees[i].xPos > 0)
        {
            bees[i].xPos += 2*scale;
        } else {
            bees[i].xPos -= 2*scale;
        }

        if (honeyPot.yPos - bees[i].yPos > 0) {
            bees[i].yPos += 2.5*scale;
        } else{
            bees[i].yPos -= 2.5*scale;
        }
    }

    for ( let i = 6; i < 8; i++) {
        if (honeyPot.xPos - bees[i].xPos > 0)
        {
            bees[i].xPos += 3*scale;
        } else {
            bees[i].xPos -= 3*scale;
        }

        if (honeyPot.yPos - bees[i].yPos > 0) {
            bees[i].yPos += 1*scale;
        } else{
            bees[i].yPos -= 1*scale;
        }
    }

    for ( let i = 8; i < bees.length - 1; i++) {
        if (honeyPot.xPos - bees[i].xPos > 0)
        {
            bees[i].xPos += 4*scale;
        } else {
            bees[i].xPos -= 4*scale;
        }

        if (honeyPot.yPos - bees[i].yPos > 0) {
            bees[i].yPos += 6*scale;
        } else{
            bees[i].yPos -= 6*scale;
        }
    }
}

function beesGotHoney() {
    for(let b = 0; b < 6; b++) {
        if(Math.abs(bees[b].xPos - honeyPot.xPos) < 10 && Math.abs(bees[b].yPos - honeyPot.yPos) < 10) {
            return true;
        }
    }
    return false;
}

function draw() {
    if (beesGotHoney()) {
        alert("the bees got their honey back!!!!!");
    }
    context.clearRect(0, 0, myCanvas.width, myCanvas.height); // clear canvas
    context.drawImage(honeyPot, honeyPot.xPos, honeyPot.yPos, 100, 100); // draw honey pot img to be at mouse position
    updateBeePosition(); // updates bee position to follow mouse
    for (let i = 0; i < bees.length; i++) {
        context.drawImage(bees[i].img, bees[i].xPos, bees[i].yPos); // draw each bee
    }


   
    if (true) {
        window.requestAnimationFrame(draw);
    }
}

window.requestAnimationFrame(draw);