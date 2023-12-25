#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const int MAX_LINE_SIZE = 128;

const char* NUMS[] = { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

//returns -1 if not number
int toNumber(char c) {
	if (c >= '0' && c <= '9') {
		return c - '0';
	}
	else
		return -1;	//not a number
}

int strToNum(char* currentLine, int* currentPos, int lineLength) {
	for (int i = 1; i < 10; i++) {
		int currentPosTemp = *currentPos;
		int currentNumberLength = strlen(NUMS[i]);
		int success = 1;
		while (currentPosTemp < lineLength && currentPosTemp - *currentPos < currentNumberLength) {
			if (currentLine[currentPosTemp] != NUMS[i][currentPosTemp - *currentPos]) {
				success = 0;
				break;
			}
			currentPosTemp++;
		}
		if (success) {
	//		*currentPos = currentPosTemp;
			*currentPos = *currentPos + 1;
			return i;
		}
	}
	return -1;
}

//find value for a given line
int decipherLine(char* currentLine) {
	int lineLength = strlen(currentLine);
	char* newLineRep = malloc(sizeof(char) * lineLength);
	if (newLineRep == NULL) {
		printf("NO SPACE FOR NEW LINE");		//malloc returned null
		return -1;
	}

	//////////////////////////////////////////
	int newLinePtr = 0, oldLinePtr = 0;
	int stnResult = -1;
	while (oldLinePtr < lineLength) {
		if (currentLine[oldLinePtr] == NULL) {
			newLineRep[newLinePtr] = currentLine[oldLinePtr];
			oldLinePtr++;
			newLinePtr++;
		} else if (toNumber(currentLine[oldLinePtr]) != -1) { 	//current char is number
			newLineRep[newLinePtr] = currentLine[oldLinePtr];
			oldLinePtr++;
			newLinePtr++;
		}
		else if ((stnResult = strToNum(currentLine, &oldLinePtr, lineLength)) == -1) {
			newLineRep[newLinePtr] = currentLine[oldLinePtr];
			oldLinePtr++;
			newLinePtr++;
		}
		else {
 			newLineRep[newLinePtr] = stnResult+'0';
			newLinePtr++;
		}
	}
	newLineRep[newLinePtr] = '\0';

	////////////////////////////////////////


	int currentChar = 0;
	int first = -1, last = -1;	//-1 until a number is found
	int newLineLength = strlen(newLineRep);
	while ((currentChar < newLineLength) && (first == -1)) {
		first = toNumber(newLineRep[currentChar]);
		currentChar++;
	}
	lineLength = strlen(newLineRep);
	currentChar = newLineLength - 1;
	while ((currentChar >= 0) && (last == -1)) {
		last = toNumber(newLineRep[currentChar]);
		currentChar--;
	}
	return (first * 10) + last;
}

int main() {
	FILE* fp;
	fp = fopen("input.txt", "r");
	if (fp == NULL) {
		printf("Error opening file\n");
		exit(1);
	}
	char* currentLine = malloc(sizeof(char) * MAX_LINE_SIZE);
	currentLine = fgets(currentLine, MAX_LINE_SIZE, fp);
	int runningTotal = 0;
	while (currentLine != NULL) {
		runningTotal += decipherLine(currentLine);
		currentLine = fgets(currentLine, MAX_LINE_SIZE, fp);
	}
	printf("Total is %d\n", runningTotal);
}