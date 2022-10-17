'use strict';

document.writeln("hello world!"); //writes to the actual page you see in the browser
window.console.log("hello world!"); //writes to the console...we use this for debugging

let myArray = [
    "hey",
    false,
    10,
    1.23,
    {
        name: "Randi",
        program: "MSD",
        printToConsole: function() {
            window.console.log("Print to console!");
        },
        season: "fall"
    }
]

for (let val of myArray) {
    window.console.log(val); // print vals of array
    // if (typeof val === "object") {
    //     for (let objVal in val) {
    //         window.console.log(`${objVal}: ${val[objVal]}`); // print key value pairs of object that is array

    //     }
    // }
}

myArray[0] = "made change here";
// when i print this out, the object opens as key value pairs in the console but the function is the second key value pair, not the third..it must have been hoisted
for (let val of myArray) {
    window.console.log(val);
}

// function is declared and defined here..if redefined later on with same name, the one last defined will be used
// i prefer to use this way
function getSum(a, b) {
    a + b
    window.console.log(a+b);
}

// this definition overrides the one above...this one will be use when getSum is called below
// function getSum(a, b) {
//     a + b
//     window.console.log(a-b);
// }

// this holds the function to execute in a variable...the value (definition) of myGetSum can change (unless we use const)
let myGetSum = function(a, b) {
    window.console.log(a + b);
}

//ints
getSum(3,4);
myGetSum(5,10);

//floats
getSum(3.51,4.23);
myGetSum(5.2,10.999);

//strings
getSum("hey, ", "how are you?"); // this and below combine the two strings into one
myGetSum("this function does", " the same thing!");

//combo
getSum("a string added to number: ", 4); // this concats int 4 into a string
myGetSum(5,10.123); // result is given the float precision 