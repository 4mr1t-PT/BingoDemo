var usedNums = new Array(76);
var unusedBNums = new Array();
var unusedINums = new Array();
var unusedNNums = new Array();
var unusedGNums = new Array();
var unusedONums = new Array();
var calledNums = new Array();
var lastString = "";
var subscriberName;
var isFirstRound =true;
var rednumber;
var bluenumber;
var greennumber;
var yellownumber;
var tealnumber;
var subscribed = false;

function newCard() {
	if(subscribed === false) {
		subscribe();
		subscribed = true;
	}
	fillLists();
	var counter = 0;
	var notSquareFree = true;
	
	for(var i=0; i < 25; i++) {
		if(counter == 0) {
			setSquare(i,0);
			counter++;
		}else if(counter == 1) {
			setSquare(i,1);
			counter++;
			console.log("i" + i);
			if(i == 11) {
				notSquareFree = false;
			} else {
				notSquareFree = true;
			}
		} else if(counter == 2 && notSquareFree) {
			setSquare(i,2);
			counter++;
		} else if(counter ==2 && !notSquareFree){
			counter++;
			continue;
			//i--;
			notSquareFree = true;
		} else if(counter == 3) {
			setSquare(i,3);
			counter++;
		}else if(counter == 4) {
			setSquare(i,4);
			counter = 0;
		}
		document.getElementById("square"+i).style.backgroundColor= '#FFFFFF';
		document.getElementById("squarecell"+i).style.backgroundColor= '#FFFFFF';
		//getRandomBingoInt(0);
	}
	//empty the current lists
	unusedBNums = new Array();
	unusedINums = new Array();
	unusedNNums = new Array();
	unusedGNums = new Array();
	unusedONums = new Array();
	fillLists();
	for(var i=25; i < 50; i++) {
		if(counter == 0) {
			setSquare(i,0);
			counter++;
		}else if(counter == 1) {
			setSquare(i,1);
			counter++;
			console.log("i" + i);
			if(i == 36) {
				notSquareFree = false;
			} else {
				notSquareFree = true;
			}
		} else if(counter == 2 && notSquareFree) {
			setSquare(i,2);
			counter++;
		} else if(counter ==2 && !notSquareFree){
			counter++;
			continue;
			notSquareFree = true;
		} else if(counter == 3) {
			setSquare(i,3);
			counter++;
		}else if(counter == 4) {
			setSquare(i,4);
			counter = 0;
		}
		document.getElementById("square"+i).style.backgroundColor= '#FFFFFF';
		document.getElementById("squarecell"+i).style.backgroundColor= '#FFFFFF';

		//getRandomBingoInt(0);
	}
}

function clicked(square,squarecell) {
	var squareData = square.innerHTML;
	if(squareData<16) {
		squareData = ("B"+squareData);
	} else if(squareData<31 && squareData>15) {
		squareData = ("I"+squareData);
	} else if(squareData<46 && squareData>30) {
		squareData = ("N" + squareData);
	} else if(squareData<61 && squareData>45) {
		squareData = ("G" + squareData);
	} else if(squareData>60) {
		squareData = ("O" + squareData);
	}
	
	if(isBingoNumberCalled(squareData)) {
		square.innerHTML = "X";
		square.style.backgroundColor = '#0C55E8';
		squarecell.style.backgroundColor = '#0C55E8';
		console.log("squaredata : "+squareData);
		console.log("this number has been called");
	} else {
		console.log("squaredata : "+squareData);
		console.log("this number hasnt been called");
	}
}

//winner publishes the winner and there username to the server.
function win() {
	if(checkBingo()) {
		console.log("winner");
		console.log("Winner subscriber: " + subscriberName);
	
		diffusion.connect({
			host : '10.1.66.73',
			port: '8080',
			principal : 'admin',
			credentials : 'password'
		}).then(function(session) {
		console.log('Publisher Connected!');
			var jsonDataType = diffusion.datatypes.json();
			var TopicSpecification = diffusion.topics.TopicSpecification;
			var TopicType = diffusion.topics.TopicType;
			return session.topics.updateValue('foo/test', jsonDataType.from( [("winner"+subscriberName)] ), jsonDataType);
		});
	} else {
		console.log("no bingo");
	}
}

function winTable2() {
	if(checkBingoTable2()) {
		console.log("winner");
		console.log("Winner subscriber: " + subscriberName);
		
		diffusion.connect({
			host : '10.1.66.73',
			port: '8080',
			principal : 'admin',
			credentials : 'password'
		}).then(function(session) {
		console.log('Publisher Connected!');
			var jsonDataType = diffusion.datatypes.json();
			var TopicSpecification = diffusion.topics.TopicSpecification;
			var TopicType = diffusion.topics.TopicType;
			return session.topics.updateValue('foo/test', jsonDataType.from( ["winner"+subscriberName] ), jsonDataType);
		});
	} else {
		console.log("no bingo");
	}
}

function checkBingo() {
	for(var i = 0; i<25; i = i+5) {
		console.log("checking horizontals");
		console.log(("square"+i).innerHTML);
		if(document.getElementById("square"+i).innerHTML == "X" && document.getElementById("square"+(i+1)).innerHTML == "X" && document.getElementById("square"+(i+2)).innerHTML == "X" && document.getElementById("square"+(i+3)).innerHTML == "X" && document.getElementById("square"+(i+4)).innerHTML == "X") {
			return true;
		} 
	}
	for(var i = 0; i<5; i++) {
		console.log(("square"+i).innerHTML);
		console.log("checking verticals");
		if(document.getElementById("square"+i).innerHTML == "X" && document.getElementById("square"+(i+5)).innerHTML == "X" && document.getElementById("square"+(i+10)).innerHTML == "X" && document.getElementById("square"+(i+15)).innerHTML == "X" && document.getElementById("square"+(i+20)).innerHTML == "X") {
			return true;
		} 
	}
	

	if(document.getElementById("square"+0).innerHTML == "X" && document.getElementById("square" +6).innerHTML == "X" && document.getElementById("square" + 18).innerHTML == "X" && document.getElementById("square"+24).innerHTML == "X") {
		return true;
	} else if(document.getElementById("square4").innerHTML == "X" && document.getElementById("square8").innerHTML == "X" && document.getElementById("square16").innerHTML == "X"&& document.getElementById("square20").innerHTML == "X"){
		return true;
	} else {
		return false;
	}
	
}

function checkBingoTable2() {
	for(i = 25; i<50; i = i+5) {
		console.log("checking horizontals");
		console.log(("square"+i).innerHTML);
		if(document.getElementById("square"+i).innerHTML === "X" && document.getElementById("square"+(i+1)).innerHTML === "X" && document.getElementById("square"+(i+2)).innerHTML === "X" && document.getElementById("square"+(i+3)).innerHTML === "X" && document.getElementById("square"+(i+4)).innerHTML === "X") {
			return true;
		} 
	}
	for(i = 25; i<30; i++) {
		console.log(("square"+i).innerHTML);
		console.log("checking verticals");
		if(document.getElementById("square"+i).innerHTML === "X" && document.getElementById("square"+(i+5)).innerHTML === "X" && document.getElementById("square"+(i+10)).innerHTML === "X" && document.getElementById("square"+(i+15)).innerHTML === "X" && document.getElementById("square"+(i+20)).innerHTML === "X") {
			return true;
		} 
	}
	if(document.getElementById("square25").innerHTML == "X" && document.getElementById("square31").innerHTML == "X" && document.getElementById("square43").innerHTML == "X" &&document.getElementById("square49").innerHTML == "X") {
		return true;
	} else if(document.getElementById("square29").innerHTML == "X" && document.getElementById("square33").innerHTML == "X" && document.getElementById("square41").innerHTML == "X"&& document.getElementById("square45").innerHTML == "X"){
		return true;
	} else {
		return false;
	}

}

function isBingoNumberCalled(value) {
	return (calledNums.includes(value));
}

function addCalledNumber(value) {
	calledNums.push(value);
}

//clear all arrays, restyle the tables for new game.
function newGame() {
	calledNums = new Array();	
	//isFirstRound = true;
	var Bstring = "B";
	var Istring = "I";
	var Nstring = "N";
	var Gstring = "G";
	var Ostring = "O";

	for(var i =1; i<16; i++) {
		document.getElementById(Bstring+ i).style.backgroundColor='#FFFFFF';

	}
	for(var i =16; i<31; i++) {
		document.getElementById(Istring+ i).style.backgroundColor='#FFFFFF';

	}
	for(var i =31; i<46; i++) {
		document.getElementById(Nstring+ i).style.backgroundColor='#FFFFFF';

	}
	for(var i =46; i<61; i++) {
		document.getElementById(Gstring+ i).style.backgroundColor='#FFFFFF';

	}
	for(var i =61; i<76; i++) {
		document.getElementById(Ostring+ i).style.backgroundColor='#FFFFFF';

	}	
	document.getElementById('tealnumber').innerHTML = "";
	document.getElementById('yellownumber').innerHTML = "";
	document.getElementById('greennumber').innerHTML = "";
	document.getElementById('bluenumber').innerHTML = "";
	document.getElementById('rednumber').innerHTML = "";	
}

//create a new username 
function createName() {
	 var text = "";
	 var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  for (var i = 0; i < 8; i++) {
    text += possible.charAt(Math.floor(Math.random() * possible.length));
  }
	subscriberName = text;
	document.getElementById('username').innerHTML = ("Username: "+text);
	return text;
}

//subscribe to the diffusion server
function subscribe() {
			createName();
	        diffusion.connect({
            // Edit these lines to include the host and port of your Diffusion server
            host : '10.1.66.73',
            port : '8080',
            // To connect anonymously you can leave out the following parameters
            principal : 'admin',
            credentials : 'password'
        }).then(function(session) {
		
			console.log("CONNECTED");
			console.log("square12 = "+ document.getElementById('square12').innerHTML);
			console.log(document.getElementById('square12').innerHTML === 'X');
            var valueStream = session.addStream('foo/test', diffusion.datatypes.json());

                valueStream.on({
					subscribe : function(topic, specification) {
						console.log("Subscribed");
					},
						unsubscribe : function(topic, specification, reason) {
						// Unsubscribed from a particular topic
					},
						value : function(topic, specification, newValue, oldValue) {
						// Value from a topic
				
						console.log("Update for " + topic, newValue.get());
					
						var outputArray = JSON.parse(newValue);
						console.log(outputArray[0]);
						//what happens when you first join the server, add numbers already called.
						if(isFirstRound === true && outputArray[0] !== null &&outputArray[0].substring(0,6) !== "winner" && outputArray[0] !== "BingoGameFinished" && outputArray[0] !== "NewGameStarting") {
							console.log(outputArray[0].substring(0,6));
							for(var i=0; i<outputArray.length; i++) {
								if(document.getElementById(outputArray[i])!== null) {
									
									document.getElementById(outputArray[i]).style.backgroundColor='#5cb6f2';
									addCalledNumber(outputArray[i]);
								}
							}
							isFirstRound = false;
						} 
						
						lastString = outputArray[outputArray.length-1];
						console.log("LAST STRING: " + lastString);
						
						//if the bingo game has finished via no one getting bingo, or winning bingo restart
						//and show the bingo tables and bingo buttons again (new tables)
						if(lastString === "BingoGameFinished") {
							document.getElementById('Winner').style.display="none";
							document.getElementById('NewGame').style.display="none";

							document.getElementById('bingotable').style.display = "";
							document.getElementById('bingotable2').style.display = "";
							document.getElementById('bingobutton1').style.display = "";
							document.getElementById('bingobutton2').style.display = "";
							anotherCard();
							newGame();
						}
						
						//what to do if bingo occurs
						//removes bingo tables and buttons and shows the winner
						if(lastString.substring(0,6)==="winner" &&outputArray.length >= 1) {
							console.log("WINNER APPROVED FROM SERVER" + lastString.substring(6));
							//bingotable.style.display = none;
							//bingotable2.style.display = none;
							document.getElementById('bingotable').style.display = "none";
							document.getElementById('bingotable2').style.display = "none";
							document.getElementById('bingobutton1').style.display = "none";
							document.getElementById('bingobutton2').style.display = "none";
							document.getElementById('Winner').innerHTML= ("The Winner Is: "+ lastString.substring(6) );
							document.getElementById('Winner').style.display = "block";
                            document.getElementById('NewGame').style.display = "block";
							document.getElementById('tealnumber').innerHTML = "O";
							document.getElementById('yellownumber').innerHTML = "G";
							document.getElementById('greennumber').innerHTML = "N";
							document.getElementById('bluenumber').innerHTML = "I";
							document.getElementById('rednumber').innerHTML = "B";
							var jsonDataType = diffusion.datatypes.json();
							var TopicSpecification = diffusion.topics.TopicSpecification;
							var TopicType = diffusion.topics.TopicType;
							return session.topics.updateValue('foo/test', jsonDataType.from( [("NewGameStarting")] ), jsonDataType);
						
						} else if(lastString.substring(0,6) !== "winner" && lastString !== "GameStarted" && lastString !== "NewGameStarting" && lastString !== "BingoGameFinished" ){
							console.log(lastString.substring(0,6));
							document.getElementById('tealnumber').innerHTML = (document.getElementById('yellownumber').innerHTML);
							document.getElementById('yellownumber').innerHTML = (document.getElementById('greennumber').innerHTML);
							document.getElementById('greennumber').innerHTML = (document.getElementById('bluenumber').innerHTML);
							document.getElementById('bluenumber').innerHTML = (document.getElementById('rednumber').innerHTML );
							document.getElementById('rednumber').innerHTML = (lastString);	
							
							
						    if(document.getElementById(lastString) !== null) {
								document.getElementById(lastString).style.backgroundColor='#5cb6f2';
							}

							addCalledNumber(outputArray[outputArray.length-1]);
						} 
						
					}
            });
            // Subscribe to the topic
            session.select('foo/test');
        });
}


function setSquare(thisSquare, i ) {
	var currSquare = "square"+thisSquare;
	var newNum;
	
	newNum = getRandomBingoInt(i);
	console.log(newNum);
	document.getElementById(currSquare).innerHTML = newNum;
}

function fillLists() {
	for(i = 1; i< 16; i++) { 
		unusedBNums.push(i);
	}
	for(i=16; i<31; i++) {
		unusedINums.push(i);
	}
	for(i=31; i<46; i++) {
		unusedNNums.push(i);
	}
	for(i=46; i<61; i++) {
		unusedGNums.push(i);
	}
	for(i=61; i<75; i++) {
		unusedONums.push(i);
	}
}

function randInt(min,max){
	return Math.floor(Math.random() * (max-min+1)+min);
}

function getRandomBingoInt(column) {
	var randomIndex;
	var randomInt;
	
	if(column == 0) {
		randomIndex = (randInt(0, unusedBNums.length-1));
		console.log("random index= " + randomIndex);		
		randomInt = unusedBNums[randomIndex];
		console.log("randomint = " + randomInt);
		unusedBNums.splice(randomIndex,1);
		console.log("unusedbnums:" + unusedBNums);
		return randomInt;
	} else if(column == 1) {
		randomIndex = (randInt(0, unusedINums.length-1))
		randomInt = unusedINums[randomIndex];
		unusedINums.splice(randomIndex,1);
		return randomInt;
	} else if(column == 2) {
		randomIndex = (randInt(0, unusedNNums.length-1))
		randomInt = unusedNNums[randomIndex];
		unusedNNums.splice(randomIndex,1);
		return randomInt;
	} else if(column == 3) {
		randomIndex = (randInt(0, unusedGNums.length-1))
		randomInt = unusedGNums[randomIndex];
		unusedGNums.splice(randomIndex,1);
		return randomInt;
	} else {
		randomIndex = (randInt(0, unusedONums.length-1))
		randomInt = unusedONums[randomIndex];
		unusedONums.splice(randomIndex,1);
		return randomInt;
	}

}

function getNewNum() {
	return Math.floor(Math.random() * 75);	
}

function anotherCard() {
	for(var i=1; i<usedNums.length; i++) {
		usedNums[i] = false;
	}	
	unusedBNums = new Array();
	unusedINums = new Array();
	unusedNNums = new Array();
	unusedGNums = new Array();
	unusedONums = new Array();
	calledNums = new Array();
	newCard();
}