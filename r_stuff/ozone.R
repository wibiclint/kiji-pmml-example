library("kernlab")
library("nnet")
library("pmml")
library("randomForest")

readCsv <- function(file){
	return (read.csv(file = file, header = TRUE))
}

writeCsv <- function(data, file){
	write.table(data, file = file, sep = ",", quote = FALSE, row.names = FALSE, col.names = TRUE)
}

writeOzone = function(values, file){
	result = data.frame(values)
	names(result) = c("O3")

	writeCsv(result, file)
}


ozoneData <- readCsv("Ozone.csv")
ozoneFormula <- formula(O3 ~ temp + ibh + ibt)

lm <- lm(ozoneFormula, ozoneData)
saveXML(pmml(lm, model.name='artifact.ozone_model'), "../work/RegressionOzone.pmml")
writeOzone(predict(lm), "../work/RegressionOzone.csv")
