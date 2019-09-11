/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var jokeBox = $('#randomJoke');
var scoreboad = $('#scoreboad');
var url = "http://localhost:8080/CA1/"

function putJoke(Joke){
    $('#joke').innerHTML = Joke.Joke;
    $('#creator').innerHTML = Joke.Creator;
    $('#score').innerHTML = Joke.Score/Joke.Votes;
}

function getRandom(){
    
}