import os

def combine_java_files_to_text(folder_path, output_file):
    # Open the output file in write mode
    with open(output_file, 'w') as outfile:
        # Loop through all files in the folder
        for root, dirs, files in os.walk(folder_path):
            for file in files:
                if file.endswith('.java'):
                    # Construct the full file path
                    file_path = os.path.join(root, file)
                    
                    # Open and read the Java file
                    with open(file_path, 'r') as infile:
                        # Write the file name as a header in the output file
                        outfile.write(f"// File: {file}\n")
                        outfile.write(infile.read())
                        outfile.write("\n\n")  # Add a separator between files

    print(f"All Java files have been combined into {output_file}")

# Example usage:
folder_path = '/home/hasnain/Desktop/ByteMe/src'  # Replace with the folder path
output_file = 'combined_java_code.txt'
combine_java_files_to_text(folder_path, output_file)

