var app = new Vue({
  el: '#magicminerApp',
  data() {
    return {
      gamesList: []
    };
  },
  mounted() {
    axios.get("/lotofacil")
      .then(response => {
         this.gamesList = _.sortBy(response.data, g => -g.gameNumber);
      })
  }
})