package com.mathworks.ci;

/*
 * Copyright 2021 The MathWorks, Inc. This Class provides MATLAB release information.
 * 
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class MatlabReleaseInfo {
    private File matlabRoot;
    private static final String VERSION_INFO_FILE = "VersionInfo.xml";
    private static final String CONTENTS_FILE = "toolbox/matlab/general/Contents.m";
    private static final String RELEASE_TAG = "release";
    private static final String VERSION_INFO_ROOT_TAG = "MathWorks_version_info";
    private static final String RELEASE_PATTERN = "\\((.*?)\\)";
    
    private Map<String, String> versionInfoCache = new HashMap<String, String>();
    
    public MatlabReleaseInfo(String matlabRoot) {
        this.matlabRoot = new File(matlabRoot);
    }

    public String getMatlabReleaseNumber() throws MatlabVersionNotFoundException {
        Map<String, String> releaseNumber = getReleaseInfoFromFile();
        return releaseNumber.get(RELEASE_TAG);
    }
    

    private Map<String, String> getReleaseInfoFromFile() throws MatlabVersionNotFoundException {
        if (ObjectUtils.isEmpty(versionInfoCache)) {
            try {
                File versionFile = new File(this.matlabRoot, VERSION_INFO_FILE);
                if(versionFile.exists()) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(versionFile);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName(VERSION_INFO_ROOT_TAG);

                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) nNode;

                            versionInfoCache.put(RELEASE_TAG, eElement.getElementsByTagName(RELEASE_TAG)
                                    .item(0).getTextContent());
                        }
                    }
                }
                else if(!this.matlabRoot.exists()){
                    throw new NotDirectoryException("Invalid matlabroot path");
                }else {
					// Get the version information from Contents.m file when VersionInfo.xml is not
					// present.
					File contentFile = new File(this.matlabRoot, CONTENTS_FILE);
					String actualVersion = null;
					try (InputStream in = new FileInputStream(contentFile);
							BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

						// Skip first line and capture the second line.
						br.readLine();
						String versionLine = br.readLine();

						Pattern p = Pattern.compile(RELEASE_PATTERN);
						Matcher m = p.matcher(versionLine);
						if (m.find()) {
                            // To return the first subsequence captured by the group
							actualVersion = m.group(1);
						}
					}
					// Update the versionInfoCache with actual version extracted from Contents.m
					versionInfoCache.put(RELEASE_TAG, actualVersion);
				}
            } catch (Exception e) {
                throw new MatlabVersionNotFoundException(
                         "MATLAB Version information not found", e);
            } 
        }
        return versionInfoCache;
    }
 }
