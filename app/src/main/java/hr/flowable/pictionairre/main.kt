package hr.flowable.pictionairre

fun main(){
  //val has = hasDuplicates(Data.filter.toTypedArray())
//  shuffle(Data.GreenTerms)
  val all = Data.BlueTerms.take(30).union(Data.GreenTerms.take(30)).union(Data.OrangeTerms.take(30)).union(Data.YellowTerms.take(30)).toSet()
  shuffle(all)
}

fun hasDuplicates(strings: Array<String>): Boolean {
  val set = HashSet<String>()
  var hasDupes = false
  for (str in strings) {
    if (!set.add(str)) {
      hasDupes = true
    }
  }

  set.forEach {
    println("\"$it\",")
  }
  return hasDupes
}

fun shuffle(data: Set<String>){
  val shuffled = data.shuffled()
  shuffled.forEach {
    println("\"$it\",")
  }
}
