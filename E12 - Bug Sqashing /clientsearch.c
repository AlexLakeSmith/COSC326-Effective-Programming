#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>


#define MAX_CLIENT_LEN 300
#define MAX_CMD_LEN 100

/**
 * client.c
 * This file reads in data from a file to find a chosen person.
 * @author Junior Dev. Corrections made by Alex Lake-Smith.
 */

struct S{
  char* firstName;
  char* lastName;
  char* phone;
  char* emailAddress;
};

/**
 * What are these variables for? You need to give explanation.
 * @varaibles: int i, j, count.
 * This makes it easier for other developers to understand your code.
 */
static int count, ind;


/** 
 * Method added to free allocated memory 
 * @param ss: The array of query structs to free.
 */
void free_all(struct S** ss){
  int i;
  for(i = 0; i < count; i++){
    free(ss[i]->firstName);
    free(ss[i]->lastName);
    free(ss[i]->phone);
    free(ss[i]->emailAddress);
    free(ss[i]);
  }
  free(ss);
}

/**
 * findFirstName function, this function is meant to
 * search through all the structs and find one that matches
 * the char* varaible s first-name.
 * @param ss: Array of structs which contain clients.
 * @param s: The person we are looking for.
 * @param count: the number of people in the structs array.
 * @return: int 1/0. 1 == client found, 0 == client not found.
 */
int findFirstName(struct S** ss, char* s, int count){
  int i = 0;
  while(i < count){
    if(strcmp(ss[i]->firstName,s) == 0){
      ind = i;
      return 1;
    }
    i++;
  }
  return 0;
}


/**
 * findLastName function, this function is meant to
 * search through all the structs and find one that matches
 * the char* varaible s last-name.
 * @param ss: Array of structs which contain clients.
 * @param s: The person we are looking for.
 * @param count: the number of people in the structs array.
 * @return: 1/0. 1 == client found, 0 == client not found.
 */
int findLastName(struct S** ss, char* s, int count){
  int i = 0;
  while(i < count){
    if(strcmp(ss[i]->lastName, s) == 0){
      ind = i;
      return 1;
    }
    i++;
  }
  return 0;
}



/**
 * findEmail function, this function is meant to
 * search through all the structs and find one that matches
 * the char* varaible s email which is passed.
 * @param ss: Array of structs which contain clients.
 * @param s: The person we are looking for.
 * @param count: the number of people in the structs array.
 * @return: 1/0. 1 == client found, 0 == client not found.
 */
int findEmail(struct S** ss, char* s, int count){
  int i = 0;
  while(i < count){
    if(strcmp(ss[i]->emailAddress, s) == 0){
      ind = i;
      return 1;
    }
    i++;
  }
  return 0;
}



/**
 * findFirstName function, this function is meant to
 * search through all the structs and find one that matches
 * the char* varaible s phone number which is passed.
 * @param ss: Array of structs which contain clients.
 * @param s: The person we are looking for.
 * @param count: the number of people in the structs array.
 * @return: 1/0. 1 == client found, 0 == client not found.
 */
int findPhone(struct S** ss, char* s, int count){
  int i = 0;
  while(i < count){
    if(strcmp(ss[i]->phone, s) == 0){
      ind = i;
      return 1;
    }
    i++;
  }
  return 0;
}        


/**
 * print_client function, this is designed to be
 * passed a certain index in the structs array and print
 * all the details of that client.
 * @param ss: Array of structs which contain clients.
 * @param ind: The index at which the client to print is located.
 */
static void print_client(struct S** ss, int ind){
  printf("First Name    : %s\n", ss[ind]->firstName);
  printf("Last Name     : %s\n", ss[ind]->lastName);
  printf("Email Address : %s\n", ss[ind]->emailAddress);
  printf("Phone Number  : %s\n\n", ss[ind]->phone);
}


/**
 * Main method. This is where program execution is run.
 * Responsible for reading in file data, storing it and parses it 
 * based on a certain user input.
 * @param argc: command line arguments to use
 * @param argv: command line arguments to use.
 * @return EXIT_SUCCESS or EXIT_FAILURE.
 */
int main(int argc, char ** argv){
  //int i;
  count = 0;
  //char buffer[10];

  //Array of structs.
  struct S** ss = (struct S**) malloc(100*sizeof(struct S**));

  // Opens file for reading in data.
  FILE *f = fopen(argv[1], "r");

  if(f != NULL){
    /* While loop to go through each individaul record.*/
    while(!feof(f)){
      /* Allocates memory and stores individual data into structs array ss.*/
            
      // Individual client data decleration and memory allocation.
      struct S* s = (struct S*) malloc(sizeof(struct S));
      s->firstName = (char *) malloc(90 * sizeof(char));
      s->lastName = (char *) malloc(90 * sizeof(char));
      s->emailAddress = (char *) malloc(90 * sizeof(char));
      s->phone = (char *) malloc(90 * sizeof(char));
      fscanf(f, "%90s %90s %90s %90s", s->firstName, s->lastName, s->phone, s->emailAddress);
      ss[count] = s;
      count++;  
    }
    fclose(f);
  } else{
    printf("Error, file not found or unreadable.\n");
    return EXIT_FAILURE;
  }


  char* val = (char*)malloc(90*sizeof(char));

  printf("%d\n", count);
    
  int command = 10;
  while(command != 5){
    printf("To query: 1: FirstName, 2: LastName, 3: Email, 4: Phone, 5: Exit.\n");

    int found = 0;
        

    // Ensures no false input is given in terms of query commands. 
    bool correct = false;
    while(correct == false){
      printf("Please enter the command query that corresponds with what you want to seach.\n");
      scanf("%d", &command);
      printf("\n");
      if(command > 0 && command < 6){
	correct = true;
      }
    }

    // To exit the program if 5 is entered.
    if(command == 5){
      printf("\nNow exiting program. Thank you.\n");
      free(val);
      free_all(ss);
      return EXIT_SUCCESS;
    }

    printf("Please enter the details to search for.\n");
    scanf("%90s", val);
    ind = -1;
    switch(command){
    case 1:
      printf("\n");
      printf("looking for first name: %s\n", val);
      found = findFirstName(ss, val, count);
      break;
    case 2:
      printf("\n");
      printf("looking for last name: %s\n", val);
      found = findLastName(ss, val, count);
      break;
    case 3:
      printf("\n");
      printf("looking for email address: %s\n", val);
      found = findEmail(ss, val, count);
      break;
    case 4:
      printf("\n");
      printf("looking for phone number: %s\n", val);
      found = findPhone(ss, val, count);
      break;
    }

    /* If successfully found person. Print details.*/
    if(found == 1){
      printf("Successfully found: %s in our records.\n", val);
      printf("\n");
      print_client(ss, ind);
    } else{
      /* Otherwise print failed to locate person in database.*/
      printf("Failed to find: %s in our records.\n", val);
      printf("\n");
    }
    ind = -1;
  }
}
