var app = new Vue({
    el: '#magicminerApp',
    data() {
        return {
            selectedGameTableId: null,
            selectedGameTableName: null,
            gameTableList: [],
            gamesList: [],
            generatedResults: [],
            randomAlgorithmSeed: null,
            geneticAlgorithmSeed: null,
            constantAlgorithmSeed: null
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
        }
    },
    methods: {
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
                    this.gamesList = _.sortBy(response.data, g => -g.gameNumber);
                });
            });
        }
    },
    mounted() {
        axios.get("/configuration/defaultGameTable").then(response => {
            axios.get("/gameTable").then(response => {
                this.gameTableList = _.sortBy(response.data, g => g.name);
            });
            this.updateSelectedTable(response.data.value);
        });
    }
})