package sakib.personal.springbootbatchupload.service.store;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStoreService {

	String storeTemporaryFile(MultipartFile multipartFile) throws IOException;
	void RemoveTemporaryFile(String fileFullPath) throws IOException;
}
