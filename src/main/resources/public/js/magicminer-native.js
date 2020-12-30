let getSelectGameTableElement = function(){
    return document.getElementById("selectGameTable");
}

let getTextInputGameTableName = function(){
    return document.getElementById("textInputGameTableName");
}

let getSelectAlgorithmElement = function(){
    return document.getElementById("selectAlgorithm");
}

let getSeed = function(){
    return document.getElementById("inputAlgorithmSeed").value;
}

let getSelectedAlgorithmName = function(){
    return document.getElementById("selectAlgorithm").value;
}

let getSelectedGameTableId = function(){
    return getSelectGameTableElement().value;
}

let writeGameNumberValid = function(gameNumber){
    let tbodyRef = document.getElementById("gameListTable").getElementsByTagName('tbody')[1];

    let rows = [...tbodyRef.rows];

    let rowIndex = _.findIndex(rows, r => {
        return gameNumber == parseInt(r.getElementsByClassName("gameNumberInputHidden")[0].value);
    });

    let row = rows.splice(rowIndex, 1)[0];

    let gameNumberTd = row.getElementsByClassName("gameNumberTd")[0];

    let count = row.getElementsByClassName("selected").length;

    if(count == 15){
        gameNumberTd.classList.add("valid");
        gameNumberTd.classList.remove("invalid");
    } else{
        gameNumberTd.classList.add("invalid");
        gameNumberTd.classList.remove("valid");
    }
}

let writeGameTableList = function(gameTableList){
    let element = getSelectGameTableElement();

    _.forEach(element.options, (o) => {
        element.remove(o);
    });

    _.forEach(gameTableList, (gt) => {
        let option = document.createElement("option");
        option.value = gt.gameTableId;
        option.text = gt.name;
        element.add(option);
    });
}

let createBallSpan = (i, classA, classB, classC) => {
    let span = document.createElement("span");
    span.classList.add(classA);
    span.classList.add(classB+i);
    span.classList.add(classC);
    let node = document.createTextNode(i+1);
    span.appendChild(node);
    return span;
}

let createBallTd = (row, classA, classB, classC) => {
    let gameTableBallsTd = row.insertCell()
    for(let i = 0 ; i < 5; i++){
        gameTableBallsTd.appendChild(createBallSpan(i, classA, classB, classC));
    }
    gameTableBallsTd.appendChild(document.createElement("br"));
    for(let i = 5 ; i < 10; i++){
        gameTableBallsTd.appendChild(createBallSpan(i, classA, classB, classC));
    }
    gameTableBallsTd.appendChild(document.createElement("br"));
    for(let i = 10 ; i < 15; i++){
        gameTableBallsTd.appendChild(createBallSpan(i, classA, classB, classC));
    }
    gameTableBallsTd.appendChild(document.createElement("br"));
    for(let i = 15 ; i < 20; i++){
        gameTableBallsTd.appendChild(createBallSpan(i, classA, classB, classC));
    }
    gameTableBallsTd.appendChild(document.createElement("br"));
    for(let i = 20 ; i < 25; i++){
        gameTableBallsTd.appendChild(createBallSpan(i, classA, classB, classC));
    }
}

let writeGameNumberList = function(gameNumberList){
    let tbodyRef = document.getElementById("gameListTable").getElementsByTagName('tbody')[1];

    let length = tbodyRef.rows.length;

    for(let i = 0 ; i < length; i++){
        tbodyRef.deleteRow(0);
    }

    _.forEach(_.sortBy(gameNumberList, gm => -parseInt(gm.gameNumber)), gm => {
        let newRow = tbodyRef.insertRow();
        newRow.classList.add("editing");

        let gameNumberSpan = document.createElement("span");
        let gameNumberInputHidden = document.createElement("input");
        gameNumberInputHidden.setAttribute("type", "hidden");
        gameNumberInputHidden.classList.add("gameNumberInputHidden");

        gameNumberInputHidden.value = gm.gameNumber;

        gameNumberSpan.appendChild(gameNumberInputHidden);
        gameNumberSpan.appendChild(document.createTextNode(gm.gameNumber));

        let gameNumberTd = newRow.insertCell();

        gameNumberTd.classList.add("gameNumberTd");
        gameNumberTd.classList.add("gameNumberTd"+gm.gameNumber);
        gameNumberTd.classList.add("invalid");
        gameNumberTd.appendChild(gameNumberSpan);
        let checkIconSpan = document.createElement("span");
        checkIconSpan.classList.add("checkIconSpan");
        checkIconSpan.innerHTML = "&#10003;";
        gameNumberTd.appendChild(checkIconSpan);

        newRow.insertCell().appendChild(document.createTextNode(gm.gameDate));

        createBallTd(newRow, "gameTableBallsTd", "gameTableBall", "ball");
        createBallTd(newRow, "algorithmBallsTd", "algorithmBall", "ball");

        let scoreNode = document.createElement("span");
        scoreNode.classList.add("scoreNode");
        newRow.insertCell().appendChild(scoreNode);

    });
}

let roundToTwo = (number) => Math.floor(number*100)/100;

let writeAlgorithmSummary = function(algorithmGameList){
    let text = "";
    let size = _.size(algorithmGameList);
    text += "O Algoritmo fez "+size+" jogos.<br>";
    let sum = _.reduce(algorithmGameList, function(memo, game){ return memo + game.score; }, 0);
    text += "A nota média foi "+roundToTwo(sum/size)+".<br>";
    let hit11 = _.size(_.filter(algorithmGameList, {score: 11}));
    if(hit11 > 0){
        text += "Acertou 11 bolas "+hit11+" vezes.<br>";
    }
    let hit12 = _.size(_.filter(algorithmGameList, {score: 12}));
    if(hit12 > 0){
        text += "Acertou 12 bolas "+hit12+" vezes.<br>";
    }
    let hit13 = _.size(_.filter(algorithmGameList, {score: 13}));
    if(hit13 > 0){
        text += "Acertou 13 bolas "+hit13+" vezes.<br>";
    }
    let hit14 = _.size(_.filter(algorithmGameList, {score: 14}));
    if(hit14 > 0){
        text += "Acertou 14 bolas "+hit14+" vezes.<br>";
    }
    let hit15 = _.size(_.filter(algorithmGameList, {score: 15}));
    if(hit15 > 0){
        text += "Acertou 15 bolas "+hit15+" vezes.<br>";
    }
    let totalPrize = (hit11*5)+(hit12*10)+(hit13*25)+(hit14*1500)+(hit15*3500000);
    let totalSpent = size*(2.5);
    text += "Gastou um total de R$"+roundToTwo(totalSpent)+".<br>";
    text += "Ganhou um total de R$"+roundToTwo(totalPrize)+".<br>";
    text += "Saldo (R$): "+(totalPrize - totalSpent)+".<br>";
    document.getElementById("resultDiv").innerHTML = text;
}

let writeAlgorithmGameList = function(algorithmGameList){
    let tbodyRef = document.getElementById("gameListTable").getElementsByTagName('tbody')[1];

    let rows = [...tbodyRef.rows];

    let sortedGameList = _.sortBy(algorithmGameList, g => -parseInt(g.gameNumber));

    _.forEach(rows, r => {

        let gameNumber = parseInt(r.getElementsByClassName("gameNumberInputHidden")[0].value);
        let gameNumberIndex = _.findIndex(sortedGameList, g => {
            return gameNumber == g.gameNumber;
        });

        let g = null;

        if(gameNumberIndex > -1){
            g = sortedGameList.splice(gameNumberIndex, 1)[0];
        }

        for(let i = 0 ; i < 25; i++){
            let gameTableBallsTd = r.getElementsByClassName("algorithmBall"+i)[0];
            if(g == null || g.ballsCompressed.charAt(i) == "0"){
                gameTableBallsTd.classList.add("notSelected");
                gameTableBallsTd.classList.remove("selected");
            } else{
                gameTableBallsTd.classList.remove("notSelected");
                gameTableBallsTd.classList.add("selected");
            }
        }
        if(!!g){
            r.getElementsByClassName("scoreNode")[0].innerHTML = g.score+"/15";
        }

    });
}

let writeGameList = function(gameList){
    let tbodyRef = document.getElementById("gameListTable").getElementsByTagName('tbody')[1];

    let rows = [...tbodyRef.rows];

    let sortedGameList = _.sortBy(gameList, g => -parseInt(g.gameNumber));

    _.forEach(rows, r => {

        let gameNumber = parseInt(r.getElementsByClassName("gameNumberInputHidden")[0].value);
        let gameNumberIndex = _.findIndex(sortedGameList, g => {
            return gameNumber == g.gameNumber;
        });

        let g = null;

        if(gameNumberIndex > -1){
            g = sortedGameList.splice(gameNumberIndex, 1)[0];
        }

        let count = 0;
        for(let i = 0 ; i < 25; i++){
            let gameTableBallsTd = r.getElementsByClassName("gameTableBall"+i)[0];
            let newClassName = "";
            if(g == null || g.ballsCompressed.charAt(i) == "0"){
                newClassName = 'notSelected';
            } else{
                count++;
                newClassName = 'selected'
            }
            gameTableBallsTd.classList.add(newClassName);

            gameTableBallsTd.onclick = function() {
                 updateBall(gameNumber, i);
            };
        }

        writeGameNumberValid(gameNumber);
    });
}

let writeAlgorithmList = function(algorithmList){
    let element = getSelectAlgorithmElement();

    _.forEach(element.options, (o) => {
        element.remove(o);
    });

    _.forEach(algorithmList, (a) => {
        let option = document.createElement("option");
        option.value = a.id.toLowerCase();
        option.text = a.name;
        element.add(option);
    });
}

let fetchGameTableList = function(callback){
    axios.get("/gameTable").then(response => {
        writeGameTableList(response.data);
        if(!!callback){
            callback();
        }
    });
}

let fetchGameList = function(){
    axios.get("/lotofacil/"+getSelectedGameTableId()).then(response => {
        writeGameList(response.data)
    });
}

let fetchGameNumberList = function(){
    axios.get("/gameNumber").then(response => {
        writeGameNumberList(response.data);
        fetchGameList();
    });
}

let fetchDefaultGameTable = function(){
    axios.get("/configuration/defaultGameTable").then(response => {
        let selectedGameTableId = parseInt(response.data.value);
        getSelectGameTableElement().value = selectedGameTableId;
        getTextInputGameTableName().value = _.findWhere(getSelectGameTableElement().options, {value: ""+selectedGameTableId}).innerHTML;
        fetchGameNumberList();
    });
}

let fetchAlgorithmList = function(){
    writeAlgorithmList([
        {id: "Constant", name: "Constante" },
        {id: "Random", name: "Aleatório" },
        {id: "Genetic", name: "Genético" }
    ]);
}

let updateSelectedGameTableName = _.debounce(function(newName){
    let selectedGameTableId = getSelectedGameTableId();
    axios.put("/gameTable", {gameTableId: selectedGameTableId, name: newName, active: true}).then(response => {
        fetchGameTableList(() => {
            getSelectGameTableElement().value = selectedGameTableId;
        });
    });
}, 1000)

let updateBall = function(gameNumber, i){
    let gameNumberTd = document.getElementsByClassName("gameNumberTd"+gameNumber)[0];
    let row = gameNumberTd.parentElement;
    let gameTableBall = row.getElementsByClassName("gameTableBall"+i)[0];
    let selected = gameTableBall.classList.contains("selected");
    if(selected){
        axios.delete("/ball/"+getSelectedGameTableId()+"/"+gameNumber+"/"+i).then(response => {
            gameTableBall.classList.add("notSelected");
            gameTableBall.classList.remove("selected");
            writeGameNumberValid(gameNumber);
        });
    } else{
        axios.post("/ball/"+getSelectedGameTableId()+"/"+gameNumber+"/"+i).then(response => {
            gameTableBall.classList.remove("notSelected");
            gameTableBall.classList.add("selected");
            writeGameNumberValid(gameNumber);
        });
    }
}

let deleteTable = function(){
    if(confirm("Você tem certeza que deseja excluir essa tabela?")){
        axios.delete("/gameTable/"+getSelectedGameTableId()).then(response => {
            fetchGameTableList(() => {
                fetchDefaultGameTable();
            });
        });
    }
}

let onchangeSelectedGameTable = function(selectedGameTableId){
    getSelectGameTableElement().value = selectedGameTableId;
    getTextInputGameTableName().value = _.findWhere(getSelectGameTableElement().options, {value: selectedGameTableId}).innerHTML;
    fetchGameNumberList();
}

let newBlankTable = function(){
    let name = window.prompt("Por favor, digite o nome da nova tabela:","Nova Tabela");
    axios.post("/gameTable/", {name: name, active: true}).then(response => {
        let selectedGameTableId = response.data.gameTableId;
        fetchGameTableList(() => {
            getSelectGameTableElement().value = selectedGameTableId;
            getTextInputGameTableName().value = _.findWhere(getSelectGameTableElement().options, {value: ""+selectedGameTableId}).innerHTML;
            fetchGameList();
        });
    });
}

let cloneTable = function(){
    if(confirm("Tem certeza que deseja clonar essa tabela?")){
        axios.post("/gameTable/"+getSelectedGameTableId()).then(response => {
            let selectedGameTableId = response.data.gameTableId;
            fetchGameTableList(() => {
                getSelectGameTableElement().value = selectedGameTableId;
                getTextInputGameTableName().value = _.findWhere(getSelectGameTableElement().options, {value: ""+selectedGameTableId}).innerHTML;
                fetchGameList();
            });
        });
    }
}

let runAlgorithm = function(){
    axios.get("/lotofacil/"+getSelectedAlgorithmName()+"?seed="+getSeed()+"&gameTableId="+getSelectedGameTableId()).then(response => {
        writeAlgorithmGameList(response.data);
        writeAlgorithmSummary(response.data);
    });
}

let startup = function(){
    fetchAlgorithmList();
    fetchGameTableList(() => {
        fetchDefaultGameTable();
    });
}


startup();