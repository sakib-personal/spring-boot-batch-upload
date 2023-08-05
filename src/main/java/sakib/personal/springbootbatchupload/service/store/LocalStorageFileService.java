package sakib.personal.springbootbatchupload.service.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class LocalStorageFileService implements FileStoreService {

	private final String fileStoreTemporaryLocation;

	public LocalStorageFileService(@Value("${file.store.temporary.location}") String fileStoreTemporaryLocation) {
		this.fileStoreTemporaryLocation = fileStoreTemporaryLocation;
	}

	public String storeTemporaryFile(MultipartFile multipartFile) throws IOException {
		log.info("fileStoreTemporaryLocation-> {}", fileStoreTemporaryLocation);
		String fileFullPath = fileStoreTemporaryLocation + multipartFile.getOriginalFilename();
		multipartFile.transferTo(new File(fileFullPath));
		return fileFullPath;
	}

	public void RemoveTemporaryFile(String fileFullPath) throws IOException {
		Files.deleteIfExists(Paths.get(fileFullPath));
	}
}
