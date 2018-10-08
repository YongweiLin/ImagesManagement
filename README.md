# ImagesManagement
A images management software that allow users to add/delete/manage tags

======================
1.Open the terminal and get to the source directory named "src"

2. Then copy and paste:
    javac application/*.java && javac controller/*.java && javac backEnd/*.java && java application.Main

=================
Warning:

1. When adding tags, user cannot add @tag within the tag.

2. Remember to click the refresh button everytime after move to file and rename the image to the previous names
    in order to get the latest information to the picture.

3. The select button is the whole history of the tags that user are enter before.

=================
Scene 1:

--Then the user has the "Choosing Image" and "Choosing A Directory" buttons to choose to get to the next step.
  1. "Choosing Image" is the button to choose an image file to load.
  2. "Choosing A Directory" is the button to choose a directory to load all images in the directory.

=================
Scene 2:

1. On the left is a list that shows all the images under this directory (including the images under the subdirectory)

2. The "Open" button is for viewing the selected image.

3. The "Edit History" button is to open a file that contains all the changing information(including the timestamp) under
this directory(such as rename the image, move the image and add delete tag).

=================
Scene 3:

1. The text-box on the left is for adding the new tag, the user can input the tag name or select by drop-down list on the right
to choose previously used tags and click the "Adding Tag" button to add the tag.

2. If the user want to delete a tag, he can just select the tag that list in the "Current Tags" block then click the "Deleting
Tag" button.

3. The "Move image to new Directory" button is for moving the image to other directory.

4. The "Back to Previous Name" button shows all the previous name that the image has and the user can select one to make the image goes back to previous name.

Note: After using the "Move image to new Directory" or "Back to Previous Name" function,
the user needs to click the "Refresh" button for the changes to be shown.
