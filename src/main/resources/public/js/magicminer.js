var app = new Vue({
    el: '#magicminerApp',
    data() {
        return {
            gamesList: [],
            generatedResults: [],
            randomAlgorithmSeed: null,
            geneticAlgorithmSeed: null
        };
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
        }
    },
    mounted() {
        axios.get("/lotofacil")
            .then(response => {
                this.gamesList = _.sortBy(response.data, g => -g.gameNumber);
            })
    }
})