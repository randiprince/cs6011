"use strict";

function findMinLocation(array, index, compareFunction) {
    let minIndex = index;
    for (let i = index+1; i < array.length; i++) {
        if (compareFunction(array[i], array[minIndex])) {
            minIndex = i;
        }
    }
    return minIndex;
}

function selectionSort(array, compareFunction) {
    for (let i = 0; i < array.length; i++) {
        let minIndex = findMinLocation(array, i, compareFunction);
        let temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
    }
}

function compareFunction(a, b) {
    return a < b; // changing to a > b sorts the array in descending order rather than ascending order
}

function compareNameByLast(a, b) {
    if (a.last === b.last) {
        return a.first < b.first;
    }
    return a.last < b.last
}

function compareNameByFirst(a, b) {
    if (a.first === b.first) {
        return a.last < b.last;
    }
    return a.first < b.first
}

function execute() {
    //integers
    let arrayInts = [8, 2, -99, 84, 0, -1, 1];
    window.console.log("Int values: " + arrayInts);
    selectionSort(arrayInts, compareFunction); // sorted from least to greatest numerical values
    window.console.log("Sorted int values: ", arrayInts);

    // floats
    let arrayFloats = [8.98, 2.22, -99.0145, 84.0987, 0.0, -1.1, 1.1, 8.97];
    window.console.log("Float values: " + arrayFloats);
    selectionSort(arrayFloats, compareFunction); // sorted from least to greatest numerical values with decimals taken into consideration
    window.console.log("Sorted float values: ", arrayFloats);

    //strings
    let arrayStrings = ["hi", "hello", "bye", "hey", "whatsuphomies", "Hi", "coding is fun!"];
    window.console.log("String values: " + arrayStrings);
    selectionSort(arrayStrings, compareFunction); // strings sorted based on ascii values. all capital letters < lowercase so Hi si first
    window.console.log("Sorted string values: ", arrayStrings);

    //mix of all three
    let arrayRandom = [8, "hello", -99.99, 84, "what is happening", -1.345, 10000000];
    window.console.log("Array values: " + arrayRandom);
    selectionSort(arrayRandom, compareFunction);
    window.console.log("Sorted random values: ", arrayRandom);

    let arrayOfPeople = [
        {first: "Olivia", last: "Benson"},
        {first: "Marlon", last: "Solomita"},
        {first: "Ryan", last: "Prince"},
        {first: "Randi", last: "Prince"},
        {first: "Hugo", last: "Squishy"},
    ];
    selectionSort(arrayOfPeople, compareNameByLast);
    window.console.log(arrayOfPeople);

    selectionSort(arrayOfPeople, compareNameByFirst);
    window.console.log(arrayOfPeople);
}

execute();