package exercises

import org.bson.Document
import submission_helper.SubmissionHelper
import com.mongodb.client.MongoClients
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.UpdateOptions
import static com.mongodb.client.model.Filters.*;

import com.mongodb.BasicDBList
import groovy.json.JsonSlurper
import groovy.json.JsonOutput


def path = 'src/main/resources/top-rated-movies.json'
//def path = 'src/main/resources/top-rated-movies-short.json'

def solution = { filePath ->
	def properties = new Properties()
	def propertiesFile = new File('src/main/resources/mongodb.properties')
	propertiesFile.withInputStream {
		properties.load(it)
	}


	def mongoClient = MongoClients.create("mongodb+srv://${properties.USN}:${properties.PWD}@cluster0.1camq.mongodb.net/${properties.DB}?retryWrites=true&w=majority")

	def db = mongoClient.getDatabase(properties.DB)
	def col = db.getCollection("movies-collections")
	
	println("dfghj")

	// TESTING CONNECTION
	println 'database: ' + db.getName()
	db.listCollectionNames().each{ println it }

	// DELETE PREVIOUS CONTENTS
def filterObject = new Document()
col.deleteMany(filterObject)

println("dfghj1.1")
	

	def txtfile= new File(filePath)
	def jsonSluper  =new  JsonSlurper()
	
	println("dfghj2")
	
	def list = jsonSluper.parseText(txtfile.text)
	def parser;

		for (obj in list)
		{
			def doc = new Document()
//			println(JsonOutput.toJson([id: 3, name: 'Rajesh', role: 'developer', isEmployee: true]))
			doc = Document.parse(JsonOutput.toJson(obj))
//			col.insertMany(doc)
			col.insertOne(doc)
		}
		

		
//		

		
		println("dfghj3")

		
//	def list2(as Document).
	def list2 = new Document()
	
	list2.putAll([title: "The Imitation Game",
				year: 2014,
				genres: [
					"Biography",
					"Drama",
					"Thriller",
					"War"
				],
				imdbRating: 8,
				actors: [
					"Benedict Cumberbatch",
					"Keira Knightley",
					"Matthew Goode"]
			])
			
			
			
//	def list2 = [[id: 4, name: 'Yi', role: 'tester', isEmployee: true], [id: 5, name: 'Pau', role: 'developer', isEmployee: true]]
//	def docList = new BasicDBList()
//	for (obj in list2) {
//		doc = new Document();
//		doc.putAll(obj)
////		docList.add(doc);
//		col.insertOne(obj)
//	}
	col.insertOne(list2)
			
	
	
	
	println("------------------1")
	
	
	def lessList= col.find(gte("imdbRating",8))
	lessList=lessList.sort{it.year + it.title}
	
	def result
	
//	result=lessList.collect{it.title.concat('_').concat(it.year)}
	result=(lessList.collect{it.title+"_"+it.year})
	
	
	println("------------------2")
	return result
}

// desk check your solution twice: both attempts should give the same result
println('first attempt:')
println(solution(path).inspect())
println('second attempt:')
println(solution(path).inspect())

// check the solution with the SubmissionHelper
// DO NOT FORGET TO SUBMIT THE CONFIRMATION NUMERIC CODE on Blackboard
SubmissionHelper.check_solution('sb947', 'week_3', solution);

