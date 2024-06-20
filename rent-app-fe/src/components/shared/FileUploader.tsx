import { useCallback, useState } from "react";
import { FileWithPath, useDropzone } from "react-dropzone";

import { Button } from "@/components/ui";
import { convertFileToUrl } from "@/lib/utils";

type FileUploaderProps = {
  fieldChange: (files: File[]) => void;
  mediaUrl: string[];
};

const FileUploader = ({ fieldChange, mediaUrl }: FileUploaderProps) => {
  const [files, setFiles] = useState<File[]>([]);
  const [fileUrls, setFileUrls] = useState<string[]>(mediaUrl);

  const onDrop = useCallback(
    (acceptedFiles: FileWithPath[]) => {
      const newFileUrls = acceptedFiles.map((file) => convertFileToUrl(file));
      setFiles((prevFiles) => [...prevFiles, ...acceptedFiles]);
      fieldChange([...files, ...acceptedFiles]);
      setFileUrls((prevUrls) => [...prevUrls, ...newFileUrls]);
    },
    [files]
  );

  const removeFile = (index: number) => {
    const newFiles = files.filter((_, i) => i !== index);
    const newFileUrls = fileUrls.filter((_, i) => i !== index);
    setFiles(newFiles);
    setFileUrls(newFileUrls);
    fieldChange(newFiles);
  };

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: {
      "image/*": [".png", ".jpeg", ".jpg"],
    },
  });

  return (
    <div
      {...getRootProps()}
      className="flex flex-center flex-col bg-dark-3 rounded-xl cursor-pointer"
    >
      <input {...getInputProps()} className="cursor-pointer" />

      {fileUrls.length > 0 ? (
        <>
          <div className="flex flex-wrap justify-center w-full p-5 lg:p-10 gap-5">
            {fileUrls.map((url, index) => (
              <div key={index} className="relative">
                <img
                  src={url}
                  alt={`image-${index}`}
                  className="file_uploader-img"
                />
                <button
                  type="button"
                  onClick={() => removeFile(index)}
                  className="absolute -right-5 -top-2 px-3 py-2 bg-primary-600 text-white rounded-full shadow-xl hover:bg-primary-500 transition-colors duration-300 ease-in-out"
                >
                  Remove
                </button>
              </div>
            ))}
          </div>
          <p className="file_uploader-label">Click or drag photo to replace</p>
        </>
      ) : (
        <div className="file_uploader-box ">
          <img
            src="/assets/icons/file-upload.svg"
            width={96}
            height={77}
            alt="file upload"
          />

          <h3 className="base-medium text-light-2 mb-2 mt-6">
            Drag photo here
          </h3>
          <p className="text-light-4 small-regular mb-6">SVG, PNG, JPG</p>

          <Button type="button" className="shad-button_dark_4">
            Select from computer
          </Button>
        </div>
      )}
    </div>
  );
};

export default FileUploader;
