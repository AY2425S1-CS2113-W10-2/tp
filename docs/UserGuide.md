# User Guide

## Introduction

Introducing ExchangeCourseMapper, the perfect assistant in your planning for your SEP in Oceania!

Using ExchangeCourseMapper, you can search and plan your course mapping by listing the universities of interest, 
along with the specific courses and subject codes offered by each school. You can quickly filter by NUS-coded modules 
or by partner universities (PU) when you want to view the relevant options. For any course mappings you are interested in,
you can save it in the Personal Tracker provided by ExchangeCourseMapper!

- [Quick Start](#Quick-Start)
- [Features](#features-)
    - [List courses provided by the partner university: `set`](#list-courses-provided-by-the-partner-university-set)
    - [Help Command: `help`](#help-command-help)
    - [Add a course mapping: `add`](#adding-a-course-mapping-add)
    - [Filter possible mappings: `filter`](#filtering-possible-mappings-filter)
    - [Delete course mapping plans from Personal Tracker: `delete`](#delete-course-mapping-plans-from-personal-tracker-delete)
    - [List out all the possible schools from the options: `list schools`](#list-out-all-the-possible-schools-from-the-options-list-schools)
    - [Obtain contacts from the list of universities: `obtain`](#obtain-contacts-from-the-list-of-universities-obtain)
- [FAQ](#FAQ)
- [Command Summary](#Command-Summary)
## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `ExchangeCourseMapper` from [here](https://github.com/AY2425S1-CS2113-W10-2/tp/releases/).
3. Download the JAR file and save it on your computer.
4. Copy the absolute path of where the jar file is saved.
5. In Terminal, run java -jar /path/to/ExchangeCourseMapper.jar
6. Time to start your planning!

## Features 


### Help Command: `help`
This feature allows users to ask for help when unsure of how the commands work or how to use the commands.
It provides users with a detailed explanation of what the command does and the format to utilise the command.

Format:
`help [COMMAND]`
* Users can use the commands feature to list out all the available commands

Example of usage:
* help filter
* help set

Expected outcome:
```
Detail explanation
Format to use the feature
Example
```

### List out all the possible schools from the options: `list schools`
List out all the schools users could possibly go for their SEP.

Format: `list schools`

Expected Output:
```
-----------------------------------------------------
The University of Melbourne
The Australian National University
Victoria University of Wellington
The University of Western Australia
-----------------------------------------------------
```


### List courses provided by the partner university: `set`
This feature allows users to list out the available mappable course that are provided by a specific partner university.

The information that would be listed out are:
- PU course code and PU course name
- NUS course code and NUS course name

Format:
`set [PARTNER_UNIVERISTY_NAME]`

The available partner universities are:
* The University of Western Australia
* The University of Melbourne
* The Australian National University
* Victoria University of Wellington

Example of usage:
* set the university of western australia
* set victoria university of wellington

Expected output:
```
PU_COURSE_CODE: PU_COURSE_NAME
NUS_COURSE_CODE: NUS_COURSE_NAME
```
* Note that the output would be a list of mappable course of the format above


### Obtain contacts from the list of universities `obtain`
Obtain the contact details of the university of interest from the list of schools available.

Format: `obtain PARTNER_UNIVERSITY_NAME /CONTACT_TYPE`

* The `PARTNER_UNIVERSITY_NAME` is the name of the partner university from the list of schools.
* The `CONTACT_TYPE` is the type of contact either number or email

Example: `obtain victoria university of wellington /number`

Expected Output:
```
Phone number for Victoria University of Wellington: +64 4 472 1000
```

Example: `obtain the university of western australia /email`

Expected Output:
```
Email for The University of Western Australia: uwa-albany@uwa.edu.au
```

Example: `obtain`

Expected Output:
```
Invalid input format
```

Example: `obtain NUS /email`

Expected Output:
```
Unknown university - nus
```

Example: `obtain the university of melbourne /fax`

Expected Output:
```
Invalid contact type.
```


### Adding a course mapping: `add`
Adds a new course mapping into `myList.json` file for storage.Course mapping is subject to validation 
to ensure that the course mapping is valid and that the university provided is an Oceania university. 

Format: `add NUS_COURSE_CODE /pu PARTNER_UNIVERSITY_NAME /coursepu PU_COURSE_CODE`

* All 3 parameters `NUS_COURSE_CODE`, `PARTNER_UNIVERISTY_NAME` and `PU_COURSE_CODE` are case-insensitive.
* Do not add punctuation to the above three parameters
* Indicate the full name of the partner university for `PARTNER_UNIVERISTY_NAME`. For example, indicate
`The Australian National University` instead of `Australian National University` or `ANU`. 

Example of usage: 
`add cs2102 /pu the university of melbourne /coursepu info20003`

Expected output:
```
You have successfully added the course: cs2102 | the university of melbourne | info20003
```
Example of usage:
`add CS3244 /pu The Australian National University /coursepu COMP3670`

Expected output:
```
You have successfully added the course: cs3244 | the australian national university | comp3670
```

Example of usage:
`add CS3244 /pu Australian National University /coursepu COMP3670`

Expected output:
```
Invalid university input!
-----------------------------------------------------
The relevant universities are (non-case sensitive):
1. The University of Melbourne
2.The Australian National University
3. Victoria University of Wellington
4.The University of Western Australia

NOTE: Please indicate the partner universities FULL NAME!
NOTE: Instead of "Australian National University," please indicate "The Australian National University."
-----------------------------------------------------
```


### Filtering possible mappings: `filter`
Filters out all possible PU courses that can be mapped to a user specified NUS course.

Format: `filter NUS_COURSE_CODE`

* The `NUS_COURSE_CODE` is in NUS course code format.

Example: `filter cs3241`

Expected Output:
   
```agsl
Partner University: The University of Melbourne
Partner University Course Code: COMP30019
-----------------------------------------------------
Partner University: The Australian National University
Partner University Course Code: COMP4610
-----------------------------------------------------
```

Example: `filter ee2026`
    
Output:

```
No courses found for the given course code.
```

### Delete course mapping plans from Personal Tracker: `delete`
Delete a course mapping plan that was initially saved into the Personal Tracker.

Format: `delete LIST_INDEX`

* The `LIST_INDEX` is the list index of the course mapping plan to be deleted.

Example: `delete 1` when there are plans stored in the Personal Tracker.

Expected Output:
```
You have deleted the course from your plan: cs2102 | the university of melbourne | info20003
```

Example: `delete 0`

Expected Output:
```
Please provide a valid index of the course plan you would like to delete.
```


### Find courses in personalised tracker
This feature allows users to search for NUS courses in their course mappings.

Format: `find [NUS_COURSE_CODE]`
* Note that this feature is searching within the personalised tracker

Example: `find cs2040`
*

Expected output:
```
cs2040 | the university of western australia | cits2200
-----------------------------------------------------
```

## FAQ

**Q**: Are the commands case-sensitive?

**A**: No they are not.

**Q**: Can I `filter` multiple courses at the same time?

**A**: No you cannot

**Q**: Can I `set` multiple schools at the same time?

**A**: No you cannot

**Q**: Can I `add` multiple courses and/or multiple schools at the same time?

**A**: No you cannot, you are only allowed to add one course mapping to one partner university at one time. 

**Q**: Can I `add` schools not in the list of schools and/or add courses not in the list?

**A**: No you cannot, you are only allowed to add partner universities in Oceania. 

**Q**: Can I `add` a course mapping to a particular partner university that is not in the database?

**A**: No you cannot, you are only allowed to add course mappings in our list to ensure the course mapping is valid. 

**Q**: Can I `obtain` multiple schools/contact types at the same time?

**A**: No you cannot 

**Q**: Can I `obtain` contacts of schools not in the list?

**A**: No you cannot

**Q**: Can I `delete` multiple indices at the same time?

**A**: No you cannot


## Command Summary

| Action           | Format, Examples                                                                                                                                                   |
|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Set**          | Format: `set PARTNER_UNIVERISTY_NAME`  <br/> Example: `set The University of Western Australia`                                                                    |
| **Help**         | Format: `help COMMAND`<br/> Example: `help filter`                                                                                                                 |
| **Add**          | Format: ` add NUS_COURSE_CODE /pu PARTNER_UNIVERSITY_NAME /coursepu PU_COURSE_CODE`<br/> Example: `add cs2102 /pu the university of melbourne /coursepu info20003` |
| **Filter**       | Format: `filter NUS_COURSE_CODE` <br/> Example: `filter cs3241`                                                                                                    |
| **Delete**       | Format:  `delete LIST_INDEX` <br/> Example: `delete 1`                                                                                                             |
| **List Schools** | Format: `list schools`                                                                                                                                             |
| **List Mapped**  | Format: `list mapped`                                                                                                                                              |
| **Obtain**       | Format: `obtain PARTNER_UNIVERSITY_NAME /CONTACT_TYPE` <br/>Example: `obtain victoria university of wellington /number`                                            |
| **Find**         | Format: `find NUS_COURSE_CODE` <br/> Example: `find cs2102`                                                                                                        |
| **Compare**      | Format: `compare pu/ UNI1 pu/ UNI2` <br/> Example: `compare pu/The University of Melbourne pu/The Australian National University`                                  |
| **Bye**          | Format: `bye`                                                                                                                                                      |
