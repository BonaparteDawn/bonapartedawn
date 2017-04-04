package github.bonapartedawn.common.service;


import github.bonapartedawn.common.abstructs.MyClassLoader;
import github.bonapartedawn.common.service.security.SecurityCoder;

import java.io.*;

public class JarClassLoader  extends MyClassLoader {
    public JarClassLoader(String name, String path) {
        super(name, path);
    }
    public JarClassLoader(String name) {
        super(name);
    }
    public JarClassLoader(SecurityCoder securityCoder, String name, String path) {
        super(securityCoder, name, path);
    }
    @Override
    public InputStream readResource(String path, String fileName, String fileTyep) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(new File(path+fileName + fileTyep)));
    }
}
