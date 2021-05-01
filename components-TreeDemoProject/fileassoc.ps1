$exts=@("sql")
foreach ($ext in $exts){
$extfile=$ext+"file"
$dotext="." + $ext
cmd /c assoc $dotext=$extfile
cmd /c "ftype $extfile=""C:\Program Files\Notepad++\notepad++.exe\"" %1"
}
