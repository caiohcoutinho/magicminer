let crawl = function(){
    let rows = Array.from(document.getElementsByTagName("body")[0].children[2].children[0].children);
    rows.shift();
    let result = "";
    for(let i = 0; i < rows.length; i++){
        let row = rows[i];
        let columns = row.children;
        for (let q = 0; q < columns.length; q++){
            let column = columns[q];
            result += ""+column.innerText.replace(/\n/g,"")+";";
        }
        result += "\n";
    }
    return result;
}
console.log(crawl());