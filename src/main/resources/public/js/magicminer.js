var app = new Vue({
    el: '#magicminerApp',
    data() {
        return {
            selectedGameTableId: null,
            selectedGameTableName: null,
            selectedGameTableLock: false,
            selectedAlgorithm: 'Constant',
            algorithmSeed: null,
            gameTableList: [],
            gameList: [],
            gameNumberList: [],
            generatedResults: []
        }
    },
    computed: {
        generatedResultByGameNumber: function(){
            let result = [];
            for(let i = 0; i < this.generatedResults.length; i++){
                let game = this.generatedResults[i];
                result[game.gameNumber] = game;
            }
            return result;
        },
        gameByGameNumber: function(){
            let result = [];
            for(let i = 0; i < this.gameList.length; i++){
                let game = this.gameList[i];
                result[game.gameNumber] = game;
            }
            return result;
        }
    },
    methods: {
        isNullOrUndefined: function(o){
            return _.isUndefined(o) || _.isNull(o);
        },
        runRandom: function(){
            axios.get("/lotofacil/random", {
                params: {
                    seed: this.randomAlgorithmSeed
                }
            }).then(response => {
                this.generatedResults = _.sortBy(response.data, g => -g.gameNumber);
            })
        },
        runGenetic: function(){
            axios.get("/lotofacil/genetic", {
                params: {
                    seed: this.geneticAlgorithmSeed
                }
            }).then(response => {
                this.generatedResults = _.sortBy(response.data, g => -g.gameNumber);
            })
        },
        runConstant: function(){
            axios.get("/lotofacil/constant", {
                params: {
                    seed: this.constantAlgorithmSeed
                }
            }).then(response => {
                this.generatedResults = _.sortBy(response.data, g => -g.gameNumber);
            })
        },
        updateSelectedTable: function(selectedGameTableId){
            this.selectedGameTableId = selectedGameTableId;
            axios.get("/gameTable/"+this.selectedGameTableId).then(response => {
                this.selectedGameTableName = response.data.name;
                axios.get("/lotofacil/"+this.selectedGameTableId).then(response => {
                    this.gameList = _.map(_.sortBy(response.data, g => -g.gameNumber), Object.freeze);
                });
            });
        },
        lockUnlockSelectedTable: function(){
            this.selectedGameTableLock = !this.selectedGameTableLock;
        },
        isGameValid: function(game){
            if(this.isNullOrUndefined(game)){
                return false;
            }
            return _.size(_.filter(game.ballsCompressed, c => c == '1')) == 15;
        },
        newBlankTable: function(){
            let name = window.prompt("Por favor, digite o nome da nova tabela:","Nova Tabela");
            axios.post("/gameTable/", {name: name}).then(response => {
                this.selectedGameTableName = name;
                this.selectedGameTableId = response.data.gameTableId;
                axios.post("/gameTable").then(response => {
                     self.gameTableList = _.sortBy(response.data, g => g.name);
                });
            });
        },
        updateBall: function(game, i){
            if(this.isNullOrUndefined(game)){
                return;
            }
            let self = this;
            let selected = game.ballsCompressed[i] == '1';
            if(selected){
                axios.delete("/ball/"+this.selectedGameTableId+"/"+game.gameNumber+"/"+i).then(response => {

                    let gameList = self.gameList;
                    let newGame = _.findWhere(gameList, {gameNumber: game.gameNumber});

                    let original = newGame.ballsCompressed;
                    let result = original.substr(0, i) + '0' + original.substr(i + 1);;
                    newGame.ballsCompressed = result;

                    self.gameList = _.map(gameList, Object.freeze);
                });
            } else{
                axios.post("/ball/"+this.selectedGameTableId+"/"+game.gameNumber+"/"+i).then(response => {

                    let gameList = self.gameList;
                    let newGame = _.findWhere(gameList, {gameNumber: game.gameNumber});

                    let original = newGame.ballsCompressed;
                    let result = original.substr(0, i) + '1' + original.substr(i + 1);;
                    newGame.ballsCompressed = result;

                    self.gameList = _.map(gameList, Object.freeze);
                });
            }
        }
    },
    created() {
        let self = this;
        this.updateSelectedGameTableName = _.debounce(function(newName){
             axios.put("/gameTable", {gameTableId: this.selectedGameTableId, name: newName}).then(response => {
                 axios.get("/gameTable").then(response => {
                     self.gameTableList = _.map(_.sortBy(response.data, g => g.name), Object.freeze);
                     self.selectedGameTableName = newName;
                 });
             });
        }, 1000);
    },
    mounted() {
        axios.get("/gameNumber").then(response => {
            this.gameNumberList = Object.freeze(_.sortBy(response.data, g =>  -g.gameNumber));
            axios.get("/configuration/defaultGameTable").then(response => {
                axios.get("/gameTable").then(response => {
                    this.gameTableList = _.map(_.sortBy(response.data, g => g.name), Object.freeze);
                });
                this.updateSelectedTable(response.data.value);
            });
        });
    }
})